package com.lyc.support.entity;

import com.lyc.simple.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/05/13 15:16 
 * @Description:
 **/

@Data
@ToString
@Entity
@Table ( name ="sys_role_resource1" )
public class RoleResource1 extends BaseEntity<String> implements Serializable {

	private static final long serialVersionUID =  8167110359541517299L;

	/**
	 * id
	 */
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", nullable = false)
	private String id;

	/**
	 * 角色id
	 */
   	@Column(name = "role_id")
	private String roleId;

	/**
	 * 客户端id
	 */
   	@Column(name = "client_id")
	private String clientId;

	/**
	 * 客户端资源id
	 */
   	@Column(name = "resource_id")
	private String resourceId;

	/**
	 * 客户端资源类型，MENU，ROUTER
	 */
   	@Column(name = "resource_type")
	private String resourceType;

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
