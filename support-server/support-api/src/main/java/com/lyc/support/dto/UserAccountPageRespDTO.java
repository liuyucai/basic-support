package com.lyc.support.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: liuyucai
 * @Created: 2023/5/2 13:04
 * @Description:
 */
@Data
@ApiModel
public class UserAccountPageRespDTO implements Serializable {

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
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
     * 出生日期
     */
    @ApiModelProperty("出生日期")
    private Date birthday;

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
     * 头像
     */
    @ApiModelProperty("头像")
    private String avatar;


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

    /**
     * 有效截止时间
     */
    @ApiModelProperty("有效截止时间")
    private Date effectiveDeadline;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
