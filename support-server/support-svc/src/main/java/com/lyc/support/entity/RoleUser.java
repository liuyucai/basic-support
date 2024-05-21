package com.lyc.support.entity;

import javax.persistence.*;
import java.io.Serializable;

import com.lyc.simple.entity.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author: liuyucai
 * @Created: 2023/05/01 21:37 
 * @Description: */

@Data
@ToString
@Entity
@Table ( name ="sys_role_user" )
public class RoleUser extends BaseEntity<String> implements Serializable {

	private static final long serialVersionUID =  1120078654962876729L;

	/**
	 * id
	 */
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", nullable = false)
	private String id;

	/**
	 * 用户id
	 */
   	@Column(name = "org_user_id")
	private String orgUserId;

	/**
	 * 角色id
	 */
   	@Column(name = "role_id")
	private String roleId;

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
