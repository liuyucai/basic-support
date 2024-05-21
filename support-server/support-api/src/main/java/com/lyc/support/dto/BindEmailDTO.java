package com.lyc.support.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lyc.simple.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/12/17 21:11
 * @Description:
 */
@Data
@ApiModel
public class BindEmailDTO extends BaseDTO<String> implements Serializable {
    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private String id;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;

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
