package com.lyc.support.entity;

import javax.persistence.*;
import java.io.Serializable;

import com.lyc.simple.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import java.util.Date;

/**
 * @author: liuyucai
 * @Created: 2023/04/23 18:20 
 * @Description:
 **/

@Data
@ToString
@Entity
@Table ( name ="oauth_client_router" )
public class ClientRouter extends BaseEntity<String> implements Serializable {

	private static final long serialVersionUID =  7507311294109824757L;

	/**
	 * id
	 */
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", nullable = false)
	private String id;

	/**
	 * 客户端id
	 */
	@Column(name = "client_id")
	private String clientId;

	/**
	 * 路由id
	 */
	@Column(name = "pid")
	private String pid;

	/**
	 * 名称
	 */
   	@Column(name = "name")
	private String name;

	/**
	 * 路由地址
	 */
   	@Column(name = "path")
	private String path;

	/**
	 * 类型
	 */
   	@Column(name = "type")
	private String type;

	/**
	 * 权限标识
	 */
   	@Column(name = "permission")
	private String permission;

	/**
	 * 是否鉴权
	 */
   	@Column(name = "authentication")
	private Integer authentication;

	/**
	 * 排序
	 */
   	@Column(name = "sort")
	private Integer sort;

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
