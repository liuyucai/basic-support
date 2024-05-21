package com.lyc.support.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/5/13 20:50
 * @Description:
 */
@Data
@ApiModel
public class RoleResourceReqDTO implements Serializable {

    /**
     * roleId
     */
    @ApiModelProperty("roleId")
    private String roleId;

    /**
     * clientId
     */
    @ApiModelProperty("clientId")
    private String clientId;


}
