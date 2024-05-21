package com.lyc.support.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/5/10 8:37
 * @Description:
 */
@Data
@ApiModel
public class UserAccountPageReqDTO implements Serializable {

    /**
     * 编号
     */
    @ApiModelProperty("编号")
    private String id;


    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String userName;

    /**
     * 真实姓名
     */
    @ApiModelProperty("真实姓名")
    private String realName;

    /**
     * 证件类型
     */
    @ApiModelProperty("证件类型")
    private String identityType;

    /**
     * 证件号码
     */
    @ApiModelProperty("证件号码")
    private String identityNo;

    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private String gender;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String phoneNo;

    /**
     * 账号是否有效，1：有效，2：禁用
     */
    @ApiModelProperty("账号是否有效，1：有效，0：禁用")
    private Integer enabled;


    /**
     * 账号类型，TEMP：临时账号，COMMON：普通账号
     */
    @ApiModelProperty("账号类型，TEMP：临时账号，COMMON：普通账号")
    private String accountType;

}
