package com.lyc.support.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/5/14 14:45
 * @Description:
 */
@Data
@ApiModel
public class RoleRouterRespDTO implements Serializable {

    /**
     * id
     */
    @ApiModelProperty("id")
    private String id;

    /**
     * 客户端id
     */
    @ApiModelProperty("客户端id")
    private String clientId;

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
     * 是否鉴权
     */
    @ApiModelProperty("是否鉴权")
    private Integer authentication;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("roleResourceId")
    private String roleResourceId;

    /**
     * 功能数量
     */
    @ApiModelProperty("功能数量")
    private Integer functionNumber;

}
