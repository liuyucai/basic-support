package com.lyc.support.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lyc.simple.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/11/16 9:13
 * @Description:
 */
@Data
@ApiModel
public class UpdateOrgUserTypeDTO extends BaseDTO<String> implements Serializable {

    @ApiModelProperty("id")
    private String id;

    /**
     * 用户类型，1：主用户，0：普通用户
     */
    @ApiModelProperty("用户类型")
    private Integer type;

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
