package com.lyc.support.dto;

import java.io.Serializable;

import com.lyc.simple.dto.BaseDTO;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * @author: liuyucai
 * @Created: 2023/04/29 23:04
 * @Description:
 **/

@Data
@ApiModel
public class UserAccountDTO extends BaseDTO<String> implements Serializable {

	private static final long serialVersionUID =  5473020335142540160L;

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
	 * 密码
	 */
   	@ApiModelProperty("密码")
	private String password;

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
	 * 微信openid
	 */
   	@ApiModelProperty("微信openid")
	private String wxOpenid;

	/**
	 * 穗好办用户号
	 */
   	@ApiModelProperty("穗好办用户号")
	private String etOpenid;

	/**
	 * 省认证平台用户号
	 */
   	@ApiModelProperty("省认证平台用户号")
	private String provinceOpenid;

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

   	@ApiModelProperty("null")
	private String salt;

    @JsonIgnore
	@Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
