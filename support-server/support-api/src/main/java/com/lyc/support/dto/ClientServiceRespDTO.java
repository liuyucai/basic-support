package com.lyc.support.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/4/17 9:07
 * @Description:
 */
@Data
@ApiModel
public class ClientServiceRespDTO implements Serializable {

    /**
     * 服务id
     */
    @ApiModelProperty("id")
    private String id;

    /**
     * 客户端服务id
     */
    @ApiModelProperty("客户端服务id")
    private String clientServiceId;

    /**
     * 服务编码
     */
    @ApiModelProperty("服务编码")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;
}
