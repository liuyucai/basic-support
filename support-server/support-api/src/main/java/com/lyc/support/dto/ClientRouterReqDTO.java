package com.lyc.support.dto;

import com.lyc.simple.annotation.Condition;
import com.lyc.simple.enmus.QueryType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/8/9 9:27
 * @Description:
 */
@Data
@ApiModel
public class ClientRouterReqDTO implements Serializable {

    /**
     * 客户端id
     */
    @ApiModelProperty("客户端id")
    private String clientId;

    /**
     * 客户端secret
     */
    @ApiModelProperty("客户端secret")
    private String clientSecret;

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


}
