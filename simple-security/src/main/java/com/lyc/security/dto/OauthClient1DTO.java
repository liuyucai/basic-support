package com.lyc.security.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/9/1 23:04
 * @Description:
 */
@Data
public class OauthClient1DTO implements Serializable {

    private String clientId;

    private String clientSecret;
}
