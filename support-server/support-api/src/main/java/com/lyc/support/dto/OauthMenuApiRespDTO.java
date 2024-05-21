package com.lyc.support.dto;

import com.lyc.simple.annotation.Like;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/11/1 9:13
 * @Description:
 */
@Data
@ApiModel
public class OauthMenuApiRespDTO implements Serializable {

    /**
     * id
     */
    @ApiModelProperty("id")
    private String id;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    @Like
    private String name;


    /**
     * 路径
     */
    @ApiModelProperty("路径")
    @Like
    private String url;

    /**
     * 来源类型，SYSTEM：系统、INSERT：手动添加
     */
    @ApiModelProperty("来源类型，SYSTEM：系统、INSERT：手动添加")
    private String sourceType;

    /**
     * 服务id
     */
    @ApiModelProperty("服务id")
    private String serviceId;

    /**
     * 请求类型
     */
    @ApiModelProperty("请求类型")
    private String requestMethod;

    /**
     * 鉴权状态，1:鉴权，0：不鉴权
     */
    @ApiModelProperty("鉴权状态，1:鉴权，0：不鉴权")
    private String authStatus;


    /**
     * menuApiId
     */
    @ApiModelProperty("menuApiId")
    private String menuApiId;



}
