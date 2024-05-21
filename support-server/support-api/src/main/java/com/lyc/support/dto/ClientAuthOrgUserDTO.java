package com.lyc.support.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/11/30 8:36
 * @Description:
 */
@Data
@ApiModel
public class ClientAuthOrgUserDTO implements Serializable {

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
     * 用户类型，1：主用户，0：普通用户
     */
    @ApiModelProperty("用户类型")
    private Integer type;

    @ApiModelProperty("orgIndex")
    private String orgIndex;
}
