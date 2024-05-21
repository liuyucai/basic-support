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
 * @Created: 2023/09/03 16:52 
 * @Description: 字典
 **/

@Data
@ToString
@Entity
@Table ( name ="sys_dict_group" )
public class DictGroup extends BaseEntity<String> implements Serializable {

	private static final long serialVersionUID =  4942440355413584810L;

	/**
	 * id
	 */
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", nullable = false)
	private String id;

	/**
	 * 字典名称
	 */
   	@Column(name = "name")
	private String name;

	/**
	 * 字典编码
	 */
   	@Column(name = "code")
	private String code;

	/**
	 * 字典类型，SYSTEM:系统类，BUSINESS
	 */
   	@Column(name = "type")
	private String type;

	/**
	 * 所属服务
	 */
   	@Column(name = "service_id")
	private String serviceId;

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
