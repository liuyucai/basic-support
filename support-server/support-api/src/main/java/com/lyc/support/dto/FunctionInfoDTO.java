package com.lyc.support.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lyc.simple.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/4/24 9:53
 * @Description:
 */
@Data
@ApiModel
public class FunctionInfoDTO extends BaseDTO<String> implements Serializable {

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
     * 权限标识
     */
    @ApiModelProperty("权限标识")
    private String permission;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;

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
