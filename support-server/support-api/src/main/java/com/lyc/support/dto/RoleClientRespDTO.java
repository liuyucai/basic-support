package com.lyc.support.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/5/13 16:02
 * @Description:
 */
@Data
@ApiModel
public class RoleClientRespDTO implements Serializable {

    /**
     * id
     */
    @ApiModelProperty("id")
    private String id;

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

    @ApiModelProperty("roleResourceId")
    private String roleResourceId;



}
