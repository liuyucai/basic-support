package com.lyc.security.service;

/**
 * @author: liuyucai
 * @Created: 2023/9/1 21:41
 * @Description:
 */
public interface SecurityService {

    void loadApiAuth(String serviceId, String applicationName);

    void loadServiceClientAuth(String serviceId, String applicationName);
}
