package com.lyc.auth.entity;

import com.lyc.simple.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: liuyucai
 * @Created: 2023/04/29 23:04 
 * @Description:
 **/

@Data
@ToString
@Entity
@Table ( name ="sys_user_account" )
public class UserAccount extends BaseEntity<String> implements Serializable {

	private static final long serialVersionUID =  5317314509712739639L;

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
	private Integer source;

	/**
	 * 账号锁定
	 */
   	@Column(name = "locked")
	private Integer locked;

	/**
	 * 账号是否有效，1：有效，2：禁用
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

	/**
	 * 账号类型，TEMP：临时账号，COMMON：普通账号
	 */
   	@Column(name = "account_type")
	private String accountType;

	/**
	 * 有效截止时间
	 */
   	@Column(name = "effective_deadline")
	private Date effectiveDeadline;

   	@Column(name = "salt")
	private String salt;

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
