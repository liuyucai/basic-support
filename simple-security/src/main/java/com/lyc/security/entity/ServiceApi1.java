package com.lyc.security.entity;

import javax.persistence.*;
import java.io.Serializable;

import com.lyc.simple.entity.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import java.util.Date;

/**
 * @author: liuyucai
 * @Created: 2023/07/29 16:36 
 * @Description:
 **/

@Data
@ToString
@Entity
@Table (name ="oauth_service_api")
public class ServiceApi1 extends BaseEntity<String> implements Serializable {

	private static final long serialVersionUID =  1939892228527944041L;

	/**
	 * id
	 */
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", nullable = false)
	private String id;

	/**
	 * 名称
	 */
   	@Column(name = "name")
	private String name;

	/**
	 * 控制器
	 */
   	@Column(name = "handler")
	private String handler;

	/**
	 * 路径
	 */
   	@Column(name = "url")
	private String url;

	/**
	 * 格式化路径
	 */
	@Column(name = "format_url")
	private String formatUrl;

	/**
	 * 来源类型，SYSTEM：系统、INSERT：手动添加
	 */
   	@Column(name = "source_type")
	private String sourceType;

	/**
	 * 服务id
	 */
   	@Column(name = "service_id")
	private String serviceId;

	/**
	 * 请求类型
	 */
   	@Column(name = "request_method")
	private String requestMethod;

	/**
	 * 鉴权状态，1:鉴权，0：不鉴权
	 */
   	@Column(name = "auth_status")
	private String authStatus;

   	@Column(name = "auth_setting")
	private String authSetting;

	@Column(name = "permission")
	private String permission;

	@Column(name = "permission_type")
	private String permissionType;

	/**
	 * 版本号
	 */
   	@Column(name = "version")
	private Integer version;

	@Column(name = "compare_status")
	private String compareStatus;

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
