package com.lyc.support.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/4/16 12:33
 * @Description:
 */
@Data
@ApiModel
public class ClientPageRespDTO implements Serializable {

    /**
     * id
     */
    @ApiModelProperty("id")
    private String id;

    /**
     * 客户端分组id
     */
    @ApiModelProperty("客户端分组id")
    private String groupId;

    /**
     * 客户端分组名称
     */
    @ApiModelProperty("客户端分组名称")
    private String groupName;

    /**
     * 客户端名称
     */
    @ApiModelProperty("客户端名称")
    private String name;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String icon;

    /**
     * 应用场景，pc、移动
     */
    @ApiModelProperty("应用场景，pc、移动")
    private String appScene;

    /**
     * 应用来源
     */
    @ApiModelProperty("应用来源")
    private String appSource;

    /**
     * 客户端密钥
     */
    @ApiModelProperty("客户端密钥")
    private String clientSecret;

    /**
     * 鉴权类型
     */
    @ApiModelProperty("鉴权类型")
    private String grantType;

    /**
     * 客户端入口
     */
    @ApiModelProperty("客户端入口")
    private String redirectUri;

    /**
     * 状态，上架、下架
     */
    @ApiModelProperty("状态，上架、下架")
    private String state;

    /**
     * 是否匿名访问
     */
    @ApiModelProperty("是否匿名访问")
    private Integer anonymous;

    /**
     * access token有效时间(分钟)
     */
    @ApiModelProperty("access token有效时间(分钟)")
    private Long accessTokenValidity;

    /**
     * 客户端描述
     */
    @ApiModelProperty("客户端描述")
    private String description;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Long sort;
}
