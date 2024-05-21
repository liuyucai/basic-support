package com.lyc.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/5/31 9:07
 * @Description:
 */
@Data
@ApiModel
public class AccountLoginDTO implements Serializable {

    private String userName;

    private String password;

    private String code;

    private String uuid;

    private String orgId;

    /**
     * 客户端密钥
     */
    @ApiModelProperty("客户端密钥")
    private String clientSecret;
}
