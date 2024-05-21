package com.lyc.support.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/4/11 9:05
 * @Description:
 */
@Data
@ApiModel
public class ClientGroupPageRespDTO implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private String id;

    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String icon;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Long sort;

    /**
     * 排序
     */
    @ApiModelProperty("客户端数量")
    private Integer clientNumber;
}
