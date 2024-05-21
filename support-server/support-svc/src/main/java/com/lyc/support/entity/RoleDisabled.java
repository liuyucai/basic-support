package com.lyc.support.entity;

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
 * @Created: 2023/05/28 15:11 
 * @Description:
 **/

@Data
@ToString
@Entity
@Table ( name ="sys_role_disabled" )
public class RoleDisabled extends BaseEntity<String> implements Serializable {

	private static final long serialVersionUID =  1467393768111888455L;

	/**
	 * id
	 */
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", nullable = false)
	private String id;

	/**
	 * 机构用户id
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
