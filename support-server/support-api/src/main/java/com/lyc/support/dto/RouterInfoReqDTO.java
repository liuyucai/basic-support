package com.lyc.support.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/4/25 8:47
 * @Description:
 */
@Data
@ApiModel
public class RouterInfoReqDTO implements Serializable {

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
     * 类型
     */
    @ApiModelProperty("类型")
    private String type;


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
     * 是否鉴权
     */
    @ApiModelProperty("是否鉴权")
    private Integer authentication;
}
