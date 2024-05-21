package com.lyc.support.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/8/9 17:49
 * @Description:
 */
@Data
@ApiModel
public class UserClientRouterDTO implements Serializable {

    /**
     * id
     */
    @ApiModelProperty("id")
    private String id;

    /**
     * 路由id
     */
    @ApiModelProperty("路由id")
    private String pid;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 路由地址
     */
    @ApiModelProperty("路由地址")
    private String path;

    /**
     * 类型
     */
    @ApiModelProperty("类型")
    private String type;

    /**
     * 权限标识
     */
    @ApiModelProperty("权限标识")
    private String permission;

    /**
     * 是否鉴权
     */
    @ApiModelProperty("是否鉴权")
    private Integer authentication;

    /**
     * 角色资源id
     */
    @ApiModelProperty("角色资源id")
    private String roleResourceId;


}
