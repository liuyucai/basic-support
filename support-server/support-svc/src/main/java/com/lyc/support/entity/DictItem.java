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
 * @Created: 2023/09/03 16:54 
 * @Description: 字典项
 **/

@Data
@ToString
@Entity
@Table ( name ="sys_dict_item" )
public class DictItem extends BaseEntity<String> implements Serializable {

	private static final long serialVersionUID =  7351240900247696372L;

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
	 * 编码
	 */
   	@Column(name = "code")
	private String code;

	/**
	 * 字典组id
	 */
	@Column(name = "group_id")
	private String groupId;

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
