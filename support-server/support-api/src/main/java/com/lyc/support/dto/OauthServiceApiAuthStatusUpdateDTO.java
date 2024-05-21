package com.lyc.support.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lyc.simple.annotation.Like;
import com.lyc.simple.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/8/12 18:38
 * @Description:
 */
@Data
@ApiModel
public class OauthServiceApiAuthStatusUpdateDTO extends BaseDTO<String> implements Serializable {

    /**
     * id
     */
    @ApiModelProperty("id")
    private String id;

    /**
     * 鉴权状态，1:鉴权，0：不鉴权
     */
    @ApiModelProperty("鉴权状态，1:鉴权，0：不鉴权")
    private String authStatus;

    @ApiModelProperty("权限设置，SYSTEM: 系统、UPDATE: 人工")
    private String authSetting;


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
