package com.lyc.support.dto;

import com.lyc.simple.annotation.Condition;
import com.lyc.simple.annotation.On;
import com.lyc.simple.enmus.QueryType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/9/2 22:43
 * @Description:
 */
@Data
@ApiModel
public class ServiceAuthReqDTO implements Serializable {

    /**
     * id
     */
    @ApiModelProperty("id")
    @Condition(columnName="id",type= QueryType.NOT_IN)
    private String id;

    /**
     * 服务编码
     */
    @ApiModelProperty("服务编码")
    @Condition(columnName="oauth_service")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    @Condition(columnName="oauth_service")
    private String name;
}
