package com.lyc.auth.entity;

import javax.persistence.*;
import java.io.Serializable;

import com.lyc.simple.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import java.util.Date;

/**
 * @author: liuyucai
 * @Created: 2023/07/24 10:55 
 * @Description:
 **/

@Data
@ToString
@Entity
@Table ( name ="sys_log_login" )
public class LogLogin extends BaseEntity<String> implements Serializable {

	private static final long serialVersionUID =  4522543985447021376L;

	/**
	 * id
	 */
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", nullable = false)
	private String id;

	/**
	 * 账号名称
	 */
   	@Column(name = "user_name")
	private String userName;

	/**
	 * 机构用户id
	 */
   	@Column(name = "org_user_id")
	private String orgUserId;

	/**
	 * 登录时间
	 */
   	@Column(name = "login_time")
	private Date loginTime;

	/**
	 * 浏览器
	 */
   	@Column(name = "browser")
	private String browser;

	/**
	 * 操作系统
	 */
   	@Column(name = "os")
	private String os;

	/**
	 * ip地址
	 */
   	@Column(name = "ip_address")
	private String ipAddress;

	/**
	 * ip所在位置
	 */
   	@Column(name = "ip_location")
	private String ipLocation;

	/**
	 * 登录信息
	 */
   	@Column(name = "login_info")
	private String loginInfo;

	/**
	 * 登录状态
	 */
	@Column(name = "status")
	private String status;

	/**
	 * 描述
	 */
	@Column(name = "msg")
	private String msg;

	/**
	 * 描述
	 */
	@Column(name = "client_id")
	private String clientId;

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}