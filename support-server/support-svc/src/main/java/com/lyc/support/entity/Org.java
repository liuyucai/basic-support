package com.lyc.support.entity;

import javax.persistence.*;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import java.util.Date;

/**
 * @author 
 * @version Id: SysOrg.java, v 0.1 2023/03/05 18:26  Exp $$
 */

@Setter
@Getter
@ToString
@Entity
@Table ( name ="sys_org" )
public class Org implements Serializable {

	private static final long serialVersionUID =  627663626889499194L;

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
	 * 组织机构索引
	 */
   	@Column(name = "org_index")
	private String orgIndex;

	/**
	 * 排序
	 */
   	@Column(name = "sort")
	private Integer sort;

}
