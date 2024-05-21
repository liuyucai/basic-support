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
 * @Created: 2023/8/12 16:18
 * @Description:
 */
@Data
@ApiModel
public class OauthServiceApiBaseDTO extends BaseDTO<String> implements Serializable {

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
     * 控制器
     */
    @ApiModelProperty("控制器")
    private String handler;

    /**
     * 路径
     */
    @ApiModelProperty("路径")
    @Like
    private String url;

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

    @ApiModelProperty("权限标识")
    private String permission;

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
