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
 * @Created: 2023/04/29 13:40 
 * @Description:
 **/

@Data
@ToString
@Entity
@Table ( name ="sys_role" )
public class Role extends BaseEntity<String> implements Serializable {

	private static final long serialVersionUID =  6960482347320115650L;

	/**
	 * id
	 */
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", nullable = false)
	private String id;

	/**
	 * 角色名称
	 */
   	@Column(name = "name")
	private String name;

	/**
	 * 所属机构
	 */
   	@Column(name = "org_id")
	private String orgId;

	/**
	 * 应用范围，本级、本级及子级
	 */
   	@Column(name = "use_scope")
	private String useScope;

	/**
	 * 数据权限
	 */
   	@Column(name = "data_scope")
	private String dataScope;

	/**
	 * 角色类型，(基础角色=base,系统角色-system,业务角色=service，默认角色)
	 */
   	@Column(name = "type")
	private String type;

	/**
	 * 排序
	 */
   	@Column(name = "sort")
	private Integer sort;

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
