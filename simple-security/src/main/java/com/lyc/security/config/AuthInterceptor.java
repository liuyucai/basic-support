package com.lyc.security.config;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.alibaba.fastjson.JSON;
import com.lyc.common.utils.StringUtils;
import com.lyc.security.dto.OauthClient1DTO;
import com.lyc.security.dto.UserDetailDTO;
import com.lyc.security.entity.ServiceApi1;
import com.lyc.security.utils.SecurityContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: liuyucai
 * @Created: 2023/6/7 10:00
 * @Description:
 */
public class AuthInterceptor implements HandlerInterceptor {

    //https://blog.csdn.net/weixin_58696998/article/details/124663181

    private String publicKey;

    private String privateKey;

    private String applicationName;

    private Boolean clientAuth;

    @Autowired
    private RedisTemplate redisTemplate;

    public AuthInterceptor(){

    }

    public AuthInterceptor(String publicKey, String privateKey,String applicationName,Boolean clientAuth){
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.applicationName = applicationName;
        this.clientAuth = clientAuth;
    }
    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {

            if (request.getMethod().equals("OPTIONS")) {
                response.setStatus(HttpServletResponse.SC_OK);
                return true;
                // 如果不是，我们就把token拿到，用来做判断
            }

            //先判断是否对客户端鉴权，是：判断客户端是否 有该服务的权限
            if(clientAuth){

                //客户端的clientId就是数据库的clientSecret
                String clientId = request.getHeader("clientId");

                boolean flag = false;
                if(StringUtils.isNotBlank(clientId)){
                    List<OauthClient1DTO> list1 = (List<OauthClient1DTO>) redisTemplate.opsForValue().get("auth:clientService:"+applicationName);
                    //获取request的header的clientSecret
                    for (int i =0;i<list1.size();i++){
                        if(list1.get(i).getClientSecret().equals(clientId)){
                            flag = true;
                            break;
                        }
                    }
                }

                if(!flag){
                    //客户端未授权
                    //会话失效
                    response.setStatus(401);

                    response.setContentType("application/json;charset=UTF-8");
                    Map<String,Object> map = new HashMap<>();
                    map.put("resultCode",6003);
                    map.put("resultDesc","客户端未授权");
                    response.getWriter().print(JSON.toJSON(map));
                    return false;
                }
            }

            //不对客户端鉴权鉴权的话，是否都是可以访问？否，不影响

            String requestMethod = request.getMethod();

            String uri = request.getRequestURI();

            //获取api列表
            List<ServiceApi1> apiList = (List<ServiceApi1>) redisTemplate.opsForValue().get("auth:api:"+applicationName);

            boolean authStatus = false;

            //匹配api,判断是否需要鉴权
            if(apiList != null && apiList.size()>0){
                for (ServiceApi1 serviceApi1 :apiList) {

                    if(serviceApi1.getRequestMethod().equals(requestMethod)){

                        boolean bool = compareUrl(uri, serviceApi1.getUrl());

                        if(bool){
                            if("1".equals(serviceApi1.getAuthStatus())){
                                authStatus = true;
                            }
                            break;
                        }
                    }
                }
            }

            //如果需要鉴权
            if(authStatus){

                //判断是否登录
                boolean verifyPermissions = verifyPermissions(request);

                if(!verifyPermissions){
                    //会话失效
                    response.setStatus(401);

                    response.setContentType("application/json;charset=UTF-8");
                    Map<String,Object> map = new HashMap<>();
                    map.put("resultCode",6002);
                    map.put("resultDesc","token失效");
                    response.getWriter().print(JSON.toJSON(map));
                    return false;
                }

                //校验api
                UserDetailDTO userDetailDTO = SecurityContextUtil.getUser();

                Map<String,List<String>> apiMap = userDetailDTO.getApiMap();

                List<String> roleApiList = apiMap.get(applicationName);

                boolean authApi = false;
                if(roleApiList != null){
                    for (String api: roleApiList) {

                        //判断是否有该api的访问权限
                        boolean compareStatus = compareUrl(uri,api);
                        if(compareStatus){
                            authApi = true;
                            break;
                        }
                    }
                }

                //如果没有访问权限
                if(!authApi){
                    //无权限访问
                    response.setStatus(401);

                    response.setContentType("application/json;charset=UTF-8");
                    Map<String,Object> map = new HashMap<>();
                    map.put("resultCode",6002);
                    map.put("resultDesc","无权限访问");
                    response.getWriter().print(JSON.toJSON(map));
                    return false;
                }
                return authApi;
            }

            return  true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;//如果设置为false时，被请求时，拦截器执行到此处将不会继续操作
        //如果设置为true时，请求将会继续执行后面的操作
    }

    /**
     * 验证是否登录
     *
     * @param request
     * @return
     */
    public boolean verifyPermissions(HttpServletRequest request) {

        try{
            String token = request.getHeader("access-token");

            if(StringUtils.isNotBlank(token)){
                //先对token进行解密
                RSA rsa = new RSA(privateKey, publicKey);

                String decryptToken = rsa.decryptStr(token, KeyType.PrivateKey);

                //decryptToken的后面10位是 random, 前面是 用户名

                String userName = decryptToken.substring(0,decryptToken.length()-10);

                String random = decryptToken.substring(decryptToken.length()-10);

                UserDetailDTO userDetailDTO = (UserDetailDTO) redisTemplate.opsForValue().get("auth:access_token:"+userName+":"+random);

                if (userDetailDTO != null) {

                    //获取该服务的api,判断api是否需要鉴权，获取该服务的角色信息，判断用户是否有该服务的权限

                    //是否需要在请求里面加client_secret? 要的话，先判断api是否需要鉴权，是？判断用户是否登录，

                    //客户端 -> 服务  ->api

                    //用户 -> 角色 -> 客户端
                    SecurityContextUtil.addUser(userDetailDTO);
                    return true;
                }
                return false;
            }else{
                return false;
            }
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    /**
     * 判断是否有权限
     *
     * @param request
     * @return
     */
    public boolean competence(HttpServletRequest request) {
//        //获取当前登录对象的全部信息
//        People people = peopleMapper.selectById(getUserId(request.getHeader(Constant.TOKEN)));
//        //管理员拥有全部权限
//        if (Constant.SUPER_ADMIN.equals(people.getUserName())) {
//            return true;
//        }
//        //判断是否被授权
//        //防止空指针
//        if (people.getStartDate() != null && people.getEndDate() != null) {
//            if (dateUtils.ifDate(people.getStartDate(), people.getEndDate(), new Date())) {
//                return true;
//            }
//        }
//        //从请求头中获取的地址
//        String requestURI = request.getRequestURI();
//        //通过角色id查询当前登陆对象的所有权限
//        List<Power> list = powerMapper.selectUrl(people.getRoleid());
//        ArrayList<String> stringList = new ArrayList<>();
//        if (!StringUtils.isEmpty(list)) {
//            list.forEach(r -> {
//                stringList.add(r.getUrl());
//            });
//            return lsitUtils.ifcontainString(stringList, requestURI);
//        }
        return true;
    }



    private boolean compareUrl(String uri,String api){



        if(api.indexOf("{")>-1){
            api = api.replaceAll("\\{.*?\\}}","*");
            String[] arr = api.split("\\*");

            //   /abc/{123}/fgh/{123}/{321}/yuf/{}    ->  /abc/*/fgh/*/*/yuf/*   ->  /abc/     /fgh/ / /yuf

            //   /abc/ 1234 /fgh/ 567/32/yuf/ 89
            String uri1 = uri;
            for(String arr1:arr){

                uri1 = uri1.replace(arr1,"");
                //下一个 那里的前面不再有  /
            }

            if(uri1.indexOf("/")>-1){
                return false;
            }else{
                return true;
            }
        }else{
            if(uri.equals(api)){
                return true;
            }
            return false;
        }
    }

}
