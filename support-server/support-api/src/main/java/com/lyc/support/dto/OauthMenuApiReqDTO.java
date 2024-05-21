package com.lyc.support.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/11/1 9:09
 * @Description:
 */
@Data
@ApiModel
public class OauthMenuApiReqDTO implements Serializable {

    /**
     * id
     */
    @ApiModelProperty("id")
    private String id;

    /**
     * 菜单id
     */
    @ApiModelProperty("菜单id")
    private String menuId;

    /**
     * 服务ids
     */
    @ApiModelProperty("服务ids")
    private String serviceIds;

    /**
     * apiUrl
     */
    @ApiModelProperty("apiUrl")
    private String apiUrl;

    /**
     * apiName
     */
    @ApiModelProperty("apiName")
    private String apiName;
}
