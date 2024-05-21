package com.lyc.support.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/5/25 10:47
 * @Description:
 */
@Data
@ApiModel
public class OrgUserRolePageReqDTO implements Serializable {


    /**
     * 用户Id
     */
    @ApiModelProperty("用户Id")
    private String userId;

    /**
     * name
     */
    @ApiModelProperty("角色名称")
    private String name;


}
