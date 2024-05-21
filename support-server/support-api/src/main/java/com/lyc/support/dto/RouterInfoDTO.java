package com.lyc.support.dto;

import com.lyc.simple.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/4/24 9:16
 * @Description:
 */
@Data
@ApiModel
public class RouterInfoDTO extends BaseDTO<String>  implements Serializable {

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
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;

    /**
     * 路由地址
     */
    @ApiModelProperty("路由地址")
    private String path;

    /**
     * 是否鉴权
     */
    @ApiModelProperty("是否鉴权")
    private Integer authentication;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
