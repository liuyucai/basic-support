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
 * @Created: 2023/3/27 11:09
 * @Description: 客户端组
 */

@Data
@ToString
@Entity
@Table ( name ="oauth_client_group" )
public class ClientGroup extends BaseEntity<String> implements Serializable {

	private static final long serialVersionUID =  351966245713343110L;

	/**
	 * 主键
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
	 * 图标
	 */
   	@Column(name = "icon")
	private String icon;

	/**
	 * 排序
	 */
   	@Column(name = "sort")
	private Long sort;

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
