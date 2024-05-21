package com.lyc.support.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lyc.simple.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/9/3 17:30
 * @Description:
 */
@Data
@ApiModel
public class DictGroupPageRespDTO extends BaseDTO<String> implements Serializable {

    /**
     * id
     */
    @ApiModelProperty("id")
    private String id;

    /**
     * 字典名称
     */
    @ApiModelProperty("字典名称")
    private String name;

    /**
     * 字典编码
     */
    @ApiModelProperty("字典编码")
    private String code;

    /**
     * 字典类型，SYSTEM:系统类，BUSINESS
     */
    @ApiModelProperty("字典类型，SYSTEM:系统类，BUSINESS")
    private String type;

    /**
     * 所属服务
     */
    @ApiModelProperty("所属服务")
    private String serviceId;

    /**
     * 所属服务名称
     */
    @ApiModelProperty("所属服务名称")
    private String serviceName;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

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
