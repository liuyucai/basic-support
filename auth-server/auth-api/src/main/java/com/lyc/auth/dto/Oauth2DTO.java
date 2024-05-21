package com.lyc.auth.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/5/31 8:37
 * @Description:
 */
@Data
@ApiModel
public class Oauth2DTO implements Serializable {


    private String username;

    private  String password;

    private String grant_type;

    private String scope;

    private String client_secret;

    private String client_id;

    private  String orgId;
}
