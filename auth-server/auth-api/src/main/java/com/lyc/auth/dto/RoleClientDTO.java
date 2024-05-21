package com.lyc.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/7/26 10:53
 * @Description:
 */
@Data
@ApiModel
public class RoleClientDTO implements Serializable {

    /**
     * id
     */
    @ApiModelProperty("id")
    private String id;

    /**
     * clientId
     */
    @ApiModelProperty("客户端id")
    private String clientId;
}
