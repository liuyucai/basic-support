package com.lyc.support.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/9/20 9:17
 * @Description:
 */
@Data
@ApiModel
public class RoleMenuResourcesDTO implements Serializable {

    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    private String roleId;

    /**
     * 客户端id
     */
    @ApiModelProperty("客户端id")
    private String clientId;

    /**
     * 菜单id
     */
    @ApiModelProperty("菜单id")
    private List<String> menuList;
}
