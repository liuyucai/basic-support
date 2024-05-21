package com.lyc.support.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/5/20 16:36
 * @Description:
 */
@Data
@ApiModel
public class OrgUserSaveDTO implements Serializable {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("机构id")
    private String orgId;

    @ApiModelProperty("用户账号id")
    private String userId;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickName;

    /**
     * 账号是否有效，1：有效，0：禁用
     */
    @ApiModelProperty("账号是否有效，1：有效，0：禁用")
    private Integer enabled;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

    /**
     * 角色
     */
    @ApiModelProperty("角色")
    private List<String> roleIds;
}
