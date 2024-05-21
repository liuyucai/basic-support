package com.lyc.support.dto;

import com.lyc.simple.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/8/13 16:27
 * @Description:
 */
@Data
@ApiModel
public class LoginUserDetailDTO implements Serializable {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("机构id")
    private String orgId;

    @ApiModelProperty("机构名称")
    private String orgName;

    @ApiModelProperty("用户账号id")
    private String userId;

    @ApiModelProperty("用户账号名称")
    private String userName;

    /**
     * 头像
     */
    private String avatar;

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
     * 角色信息
     */
    @ApiModelProperty("角色信息")
    private List<RoleDTO> roleList;

    /**
     * 机构信息
     */
    @ApiModelProperty("机构信息")
    private List<OrgDTO> orgList;
}
