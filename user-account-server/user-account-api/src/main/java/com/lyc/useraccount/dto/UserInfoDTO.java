package com.lyc.useraccount.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: liuyucai
 * @Created: 2023/6/8 11:25
 * @Description:
 */
@Data
@ApiModel
public class UserInfoDTO implements Serializable {

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
     * 实名认证
     */
    @ApiModelProperty("实名认证")
    private Integer certification;

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
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;

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
     * 数据来源
     */
    @ApiModelProperty("数据来源")
    private Integer source;

    /**
     * 账号锁定
     */
    @ApiModelProperty("账号锁定")
    private Integer locked;

    /**
     * 账号是否有效，1：有效，2：禁用
     */
    @ApiModelProperty("账号是否有效，1：有效，0：禁用")
    private Integer enabled;

    /**
     * 业务对象ID
     */
    @ApiModelProperty("业务对象ID")
    private String objectId;

    /**
     * 重置初始密码: 0 = 未修改初始密码,1=已修改初始密码
     */
    @ApiModelProperty("重置初始密码: 0 = 未修改初始密码,1=已修改初始密码")
    private Integer resetInitPwd;

    /**
     * 更新密码时间
     */
    @ApiModelProperty("更新密码时间")
    private Date passwordUpdateTime;

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

    /**
     * 机构用户ID
     */
    @ApiModelProperty("机构用户ID")
    private String orgUserId;

    /**
     * 机构ID
     */
    @ApiModelProperty("机构ID")
    private String orgId;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickName;
}
