package com.lyc.useraccount.config;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: liuyucai
 * @Created: 2023/6/7 10:00
 * @Description:
 */
public class RequestInterceptor extends AntPathMatcher implements HandlerInterceptor {

    //https://blog.csdn.net/weixin_58696998/article/details/124663181

    private String publicKey;

    private String privateKey;

    @Autowired
    private RedisTemplate redisTemplate;

    public RequestInterceptor(){

    }

    public RequestInterceptor(String publicKey, String privateKey){
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }


    public boolean matchPathPattern(String url){
        for (String pattern: ExcludePatternsConfig.excludePatterns){
            if(super.match(pattern,url)){
                return true;
            }
        }
        return false;
    }

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {

            //判断是否登录
            boolean verifyPermissions = verifyPermissions(request);

            if(!verifyPermissions){

//                response.setStatus(401);
//                response.getWriter().write("设置响应状态码成功");
                return false;
            }
            //判断是否有权限
            boolean competence = competence(request);
            if (competence) {
                return true;
            }
            return false;
            //这里设置拦截以后重定向的页面，一般设置为登陆页面地址
//            response.sendRedirect(request.getContextPath() + "/error.html");
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
        String token = request.getHeader("access-token");

        if(StringUtils.isNotBlank(token)){
            //先对token进行解密

            RSA rsa = new RSA(privateKey, publicKey);

            String decryptToken = rsa.decryptStr(token, KeyType.PrivateKey);

            //decryptToken的后面10位是 random, 前面是 用户名

            String userName = decryptToken.substring(0,decryptToken.length()-10);
            String random = decryptToken.substring(decryptToken.length()-10);

            Object o = redisTemplate.opsForValue().get("auth:access_token:"+userName+":"+random);
            if (o != null) {
                return true;
            }
            return false;
        }else{
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
        return false;
    }

}
