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
 * @Created: 2023/04/28 22:35 
 * @Description:
 **/

@Data
@ToString
@Entity
@Table ( name ="sys_org" )
public class Org extends BaseEntity<String> implements Serializable {

	private static final long serialVersionUID =  4186685510418272482L;

	/**
	 * 编号
	 */
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", nullable = false)
	private String id;

	/**
	 * 机构名称
	 */
   	@Column(name = "name")
	private String name;

	/**
	 * 上级组织id
	 */
   	@Column(name = "pid")
	private String pid;

	/**
	 * 状态
	 */
   	@Column(name = "state")
	private Integer state;

	/**
	 * 组织机构索引
	 */
   	@Column(name = "org_index")
	private String orgIndex;

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
