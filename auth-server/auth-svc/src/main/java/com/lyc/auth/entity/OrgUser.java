package com.lyc.auth.entity;

import com.lyc.simple.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/05/16 16:58 
 * @Description:
 **/

@Data
@ToString
@Entity
@Table ( name ="sys_org_user" )
public class OrgUser extends BaseEntity<String> implements Serializable {

	private static final long serialVersionUID =  2661499841972941463L;

    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", nullable = false)
	private String id;

   	@Column(name = "org_id")
	private String orgId;

   	@Column(name = "user_id")
	private String userId;

	/**
	 * 用户昵称
	 */
   	@Column(name = "nick_name")
	private String nickName;

	/**
	 * 账号是否有效，1：有效，0：禁用
	 */
   	@Column(name = "enabled")
	private Integer enabled;

	/**
	 * 用户类型，1：主用户，0：普通用户
	 */
	@Column(name = "type")
	private Integer type;

	/**
	 * 描述
	 */
   	@Column(name = "description")
	private String description;

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
