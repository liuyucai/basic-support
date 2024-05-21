package com.lyc.support.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lyc.simple.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/4/16 13:17
 * @Description:
 */
@Data
@ApiModel
public class ClientBaseDTO extends BaseDTO<String> implements Serializable {

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

    @JsonIgnore
    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
