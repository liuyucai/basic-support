package com.lyc.security.manager;

import com.lyc.common.utils.StringUtils;
import com.lyc.security.annotation.Auth;
import com.lyc.security.dto.ServiceApi1DTO;
import com.lyc.security.entity.OauthClient1;
import com.lyc.security.entity.ServiceApi1;
import com.lyc.security.service.SecurityService;
import com.lyc.security.service.ServiceApiService1;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * @author: liuyucai
 * @Created: 2023/7/29 10:17
 * @Description:
 */
@Component
//@Order(value = 2)
public class StarterManager  implements ApplicationContextAware, CommandLineRunner {


    @Value("${spring.application.name:''}")
    private String applicationName;

    @Value("${lyc.auth.enabled:false}")
    private Boolean authEnabled;

    @Value("${lyc.security.clientAuth:false}")
    private Boolean clientAuth;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    ServiceApiService1 serviceApiService1;

    @Autowired
    SecurityService securityService;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) throws Exception {


        //获取服务的id,
        String id = serviceApiService1.getServiceId(applicationName);

        if(StringUtils.isBlank(id)){
            return;
        }
        //是否要加载  服务、客户端？
        if(clientAuth){
            //加载该服务的授权 客户端信息
            securityService.loadServiceClientAuth(id,applicationName);
        }

        //是否对 服务 ->  服务 鉴权？   用于  feign 间的服务调用          auth:service:service

        //是否开启异步？

        //先去获取该服务的 api信息

        if(!authEnabled){
            return;
        }

        //获取已添加的api
        List<ServiceApi1> apiList = serviceApiService1.getAllList(id);

        //获取已添加的api
        List<ServiceApi1> compareSuccessApiList = new ArrayList<>(apiList.size());

        Set<String> result = new TreeSet<>();
        RequestMappingHandlerMapping bean = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = bean.getHandlerMethods();

        //已存在的api
        Set<String> hasExistApiList = new TreeSet<>();

        //没扫描到的api    结束后，给没扫描到的api标志

        //保存一个存到redis里面的数据？更新完成后，再读取数据库的存到redis里面？怎么确定redis里面的是最新的

        //自己的也存一份
        //redis： auth：服务：api
        handlerMethods.forEach((key,value)->{

            RequestMappingInfo rmi = key;

            HandlerMethod handlerMethod = value;

            //获取RequestMethod
            Set<RequestMethod> requestMethods = rmi.getMethodsCondition().getMethods();

            //如果size==0，则就是RequestMapping,就比对五个 POST、GET、PUT、DELETE、PATCH

            //如果url有{}的
            PatternsRequestCondition pc = rmi.getPatternsCondition();
            //获取url
            Set<String> pSet = pc.getPatterns();

            if(requestMethods.size()>0){
                //比对
                requestMethods.forEach(requestMethod -> {
                    compareTo(id,requestMethod.name(),handlerMethod,rmi.getName(),pSet,apiList,hasExistApiList,compareSuccessApiList);
                });
                //比对成功，相等：判断信息是否有变化，是否变为鉴权，不相等，增加
            }else{
                //比对五个 POST、GET、PUT、DELETE、PATCH
                compareTo(id,"POST",handlerMethod,rmi.getName(),pSet,apiList,hasExistApiList,compareSuccessApiList);

                compareTo(id,"GET",handlerMethod,rmi.getName(),pSet,apiList,hasExistApiList,compareSuccessApiList);

                compareTo(id,"PUT",handlerMethod,rmi.getName(),pSet,apiList,hasExistApiList,compareSuccessApiList);

                compareTo(id,"DELETE",handlerMethod,rmi.getName(),pSet,apiList,hasExistApiList,compareSuccessApiList);

                compareTo(id,"PATCH",handlerMethod,rmi.getName(),pSet,apiList,hasExistApiList,compareSuccessApiList);
            }
            result.addAll(pSet);
        });
        result.forEach(url -> {
            System.out.println(url);
        });

        //是否需要给没有扫描到的api做下标记？  有个标记状态，扫到就标1,没扫到就标0   compareStatus
        apiList.forEach(serviceApi->{

            boolean status = false;
            for(int i=0;i<compareSuccessApiList.size();i++){
                if(serviceApi.getId().equals(compareSuccessApiList.get(i).getId())){
                    status = true;
                    break;
                }
            }

            if(!status){
                //更新状态
                if("1".equals(serviceApi.getCompareStatus())){
                    serviceApiService1.updateCompareStatus(serviceApi.getId(),"0");
                }
            }
        });

        //加载所有需要鉴权的api，存到redis上面；默认都是鉴权的，

        //获取已添加的api
//        List<ServiceApi1> apiList1 = serviceApiService1.getAllList(id);
//
//        //
//        redisTemplate.opsForValue().set("auth:api:"+applicationName,apiList1);

        securityService.loadApiAuth(id,applicationName);

        //一般默认都是不鉴权的，只加载鉴权的
    }

    private void compareTo(String serviceId, String requestMethod, HandlerMethod handlerMethod, String apiName, Set<String> pSet, List<ServiceApi1> apiList, Set<String> hasExistApiList, List<ServiceApi1> compareSuccessApiList){
        pSet.forEach(url -> {

            //排除不属于服务的url
            if("/error".equals(url) || url.contains("api-docs") || url.contains("swagger")){
                return;
            }
            ServiceApi1 serviceApi1 = compareUrl(requestMethod,url,apiList);

            if(serviceApi1 != null){
                //说明重复了，判断是否需要更改信息  重复了，但是{*}里面的参数可能不一样，需要判断是否更正
                //如果系统增加的url和请求类型相同，人工修改权限，以人工为准
                //插入数据库
                saveApi(serviceId, serviceApi1,handlerMethod,requestMethod,apiName,url);

                //说明没有
                String key1 = requestMethod+"_"+url;
                key1 = key1.replaceAll("\\{.*?\\}}","");
                hasExistApiList.add(key1);

                //
                compareSuccessApiList.add(serviceApi1);
            }else{
                //说明没有

                //GET_URL
                String key1 = requestMethod+"_"+url;
                key1 = key1.replaceAll("\\{.*?\\}}","");
                //判断是否已在待添加的列表中了 url+requestMethod  去除{}里面的
                if(!hasExistApiList.contains(key1)){
                    //已经存在了
                    //新增
                    saveApi(serviceId, serviceApi1,handlerMethod,requestMethod,apiName,url);
                    hasExistApiList.add(key1);
                }
            }
        });
    }


    private ServiceApi1 compareUrl(String requestMethod, String url, List<ServiceApi1> apiList){

        //判断
        String url1 = url.replaceAll("\\{.*?\\}}","");

        for(ServiceApi1 serviceApi1 :apiList){
//            String temp = serviceApi1.getUrl().replaceAll("\\{.*?\\}}","");
            if(url1.equals(serviceApi1.getFormatUrl()) && serviceApi1.getRequestMethod().equals(requestMethod)){
                return serviceApi1;
            }
        }
        return null;
    };



    private void saveApi(String serviceId, ServiceApi1 serviceApi1, HandlerMethod handlerMethod, String requestMethod, String apiName, String url){

        //先判断是否信息变化，变化就更新，不变化就不用


        //Controller
        String handlerName = handlerMethod.getMethod().getDeclaringClass().getName();

        //name
        if(StringUtils.isBlank(apiName)){
            //判断是否有注解
            if(handlerMethod.hasMethodAnnotation(ApiOperation.class)){
                ApiOperation apiOperation = handlerMethod.getMethodAnnotation(ApiOperation.class);
                apiName = apiOperation.value();
            }
        }

        if(serviceApi1 == null){
            ServiceApi1DTO serviceApi1DTO = new ServiceApi1DTO();
            //新增的
            serviceApi1DTO.setSourceType("SYSTEM");
            serviceApi1DTO.setRequestMethod(requestMethod);
            serviceApi1DTO.setServiceId(serviceId);
            serviceApi1DTO.setHandler(handlerName);

            serviceApi1DTO.setName(apiName);

            //url
            serviceApi1DTO.setUrl(url);

            String formatUrl = url.replaceAll("\\{.*?\\}}","");

            serviceApi1DTO.setFormatUrl(formatUrl);

            //判断是否更改权限，默认都是鉴权的
            if(handlerMethod.hasMethodAnnotation(Auth.class)){
                Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
                if(auth.value()){
                    serviceApi1DTO.setAuthStatus("1");
                }else{
                    serviceApi1DTO.setAuthStatus("0");
                }

                if(StringUtils.isNotBlank(auth.permission())){
                    //设置权限编码
                    serviceApi1DTO.setPermission(auth.permission());
                    serviceApi1DTO.setPermissionType("CODE");
                }
            }else{
                serviceApi1DTO.setAuthStatus("1");
            }

            if(StringUtils.isBlank(serviceApi1DTO.getPermission())){
                //设置权限编码
                serviceApi1DTO.setPermission(UUID.randomUUID().toString());
                serviceApi1DTO.setPermissionType("SYSTEM");
            }

            serviceApi1DTO.setAuthSetting("SYSTEM");

            //版本号
            serviceApi1DTO.setVersion(1);
            //比对状态
            serviceApi1DTO.setCompareStatus("1");

            serviceApiService1.save(serviceApi1DTO);
        }else{

            //修改,判断信息是否变化

            //来源类型，SYSTEM：系统、INSERT：手动添加    //如果是系统自动添加的，才会更新，如果是用户添加的，不用管
            if("SYSTEM".equals(serviceApi1.getSourceType())){
                //权限设置，SYSTEM: 系统、UPDATE: 人工
                if("SYSTEM".equals(serviceApi1.getAuthSetting())){

                    //判断信息是否变化

                    boolean changeStatus = false;

                    //判断是否更改权限，默认都是鉴权的
                    String authStatus = null;
                    if(handlerMethod.hasMethodAnnotation(Auth.class)){
                        Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

                        //true :鉴权
                        if(auth.value()){
                            authStatus = "1";
                        }else{
                            authStatus = "0";
                        }

                        if(StringUtils.isNotBlank(auth.permission()) && !auth.permission().equals(serviceApi1.getPermission())){
                            changeStatus = true;
                            //设置权限编码
                            serviceApi1.setPermission(auth.permission());
                            serviceApi1.setPermissionType("CODE");
                        }
                    }else{
                        authStatus = "1";
                    }

                    //如果没有的话
                    if(StringUtils.isBlank(serviceApi1.getPermission())){
                        changeStatus = true;
                        //设置权限编码
                        serviceApi1.setPermission(UUID.randomUUID().toString());
                        serviceApi1.setPermissionType("SYSTEM");
                    }

                    if(!authStatus.equals(serviceApi1.getAuthStatus())){
                        changeStatus = true;
                        serviceApi1.setAuthStatus(authStatus);
                    }
                    //判断 handlerName
                    if(!handlerName.equals(serviceApi1.getHandler())){
                        changeStatus = true;
                        serviceApi1.setHandler(handlerName);
                    }

                    //判断apiName
                    if(apiName != null){
                        if(!apiName.equals(serviceApi1.getName())){
                            changeStatus = true;
                            serviceApi1.setName(apiName);
                        }
                    }

                    if(changeStatus){
                        ServiceApi1DTO serviceApi1DTO = new ServiceApi1DTO();
                        BeanUtils.copyProperties(serviceApi1, serviceApi1DTO);
                        int version = serviceApi1DTO.getVersion() == null?0: serviceApi1DTO.getVersion();
                        //版本号
                        serviceApi1DTO.setVersion(version+1);
                        //比对状态
                        serviceApi1DTO.setCompareStatus("1");
                        serviceApiService1.update(serviceApi1DTO);
                    }
                }
            }
        }

    }
}
