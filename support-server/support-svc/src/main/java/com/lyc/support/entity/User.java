package com.lyc.support.entity;

import javax.persistence.*;
import java.io.Serializable;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import java.util.Date;

/**
 * @author 
 * @version Id: SysUser.java, v 0.1 2023/02/21 09:05  Exp $$
 */

@Data
@ToString
@Entity
@Table ( name ="sys_user" )
public class User implements Serializable {

	private static final long serialVersionUID =  1676127078573831378L;

	/**
	 * 用户ID
	 */
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", nullable = false)
	private String id;

	/**
	 * 用户名
	 */
   	@Column(name = "user_name")
	private String userName;

	/**
	 * 密码
	 */
   	@Column(name = "password")
	private String password;

	/**
	 * 真实姓名
	 */
   	@Column(name = "real_name")
	private String realName;

	/**
	 * 证件类型
	 */
   	@Column(name = "identity_type")
	private String identityType;

	/**
	 * 证件号码
	 */
   	@Column(name = "identity_no")
	private String identityNo;

	/**
	 * 实名认证
	 */
   	@Column(name = "certification")
	private Integer certification;

	/**
	 * 出生日期
	 */
   	@Column(name = "birthday")
	private Date birthday;

	/**
	 * 性别
	 */
   	@Column(name = "gender")
	private String gender;

	/**
	 * 邮箱
	 */
   	@Column(name = "email")
	private String email;

	/**
	 * 手机号码
	 */
   	@Column(name = "phone_no")
	private String phoneNo;

	/**
	 * 头像
	 */
   	@Column(name = "avatar")
	private String avatar;

	/**
	 * 微信openid
	 */
   	@Column(name = "wx_openid")
	private String wxOpenid;

	/**
	 * 穗好办用户号
	 */
   	@Column(name = "et_openid")
	private String etOpenid;

	/**
	 * 省认证平台用户号
	 */
   	@Column(name = "province_openid")
	private String provinceOpenid;

	/**
	 * 数据来源
	 */
   	@Column(name = "source")
	private Long source;

	/**
	 * 账号锁定
	 */
   	@Column(name = "locked")
	private Integer locked;

	/**
	 * 账号是否有效
	 */
   	@Column(name = "enabled")
	private Integer enabled;

	/**
	 * 业务对象ID
	 */
   	@Column(name = "object_id")
	private String objectId;

	/**
	 * 重置初始密码: 0 = 未修改初始密码,1=已修改初始密码
	 */
   	@Column(name = "reset_init_pwd")
	private Integer resetInitPwd;

	/**
	 * 更新密码时间
	 */
   	@Column(name = "password_update_time")
	private Date passwordUpdateTime;
}
