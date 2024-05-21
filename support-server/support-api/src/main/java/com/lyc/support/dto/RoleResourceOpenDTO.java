package com.lyc.support.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/6/10 22:50
 * @Description:
 */
@Data
@ApiModel
public class RoleResourceOpenDTO implements Serializable {

    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    private String roleId;

    /**
     * 客户端id
     */
    @ApiModelProperty("客户端id")
    private String clientId;

    /**
     * 客户端资源id
     */
    @ApiModelProperty("客户端资源id")
    private String resourceId;
}
