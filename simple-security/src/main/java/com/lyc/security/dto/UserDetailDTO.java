package com.lyc.security.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: liuyucai
 * @Created: 2023/6/7 9:30
 * @Description:
 */
@Data
public class UserDetailDTO implements Serializable{


    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 证件类型
     */
    private String identityType;

    /**
     * 证件号码
     */
    private String identityNo;

    /**
     * 实名认证
     */
    private Integer certification;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 性别
     */
    private String gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phoneNo;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 微信openid
     */
    private String wxOpenid;

    /**
     * 穗好办用户号
     */
    private String etOpenid;

    /**
     * 省认证平台用户号
     */
    private String provinceOpenid;

    /**
     * 数据来源
     */
    private Integer source;

    /**
     * 账号锁定
     */
    private Integer locked;

    /**
     * 账号是否有效，1：有效，2：禁用
     */
    private Integer enabled;

    /**
     * 业务对象ID
     */
    private String objectId;

    /**
     * 重置初始密码: 0 = 未修改初始密码,1=已修改初始密码
     */
    private Integer resetInitPwd;

    /**
     * 更新密码时间
     */
    private Date passwordUpdateTime;

    /**
     * 账号类型，TEMP：临时账号，COMMON：普通账号
     */
    private String accountType;

    /**
     * 有效截止时间
     */
    private Date effectiveDeadline;

    /**
     * 机构用户ID
     */
    private String orgUserId;

    /**
     * 机构ID
     */
    private String orgId;

    /**
     * 机构orgIndex
     */
    private String orgIndex;

    /**
     * 用户昵称
     */
    private String nickName;

    private List<String> roleList;


    private Map<String,List<String>> apiMap;


}
