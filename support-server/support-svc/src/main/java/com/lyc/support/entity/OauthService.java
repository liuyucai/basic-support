package com.lyc.support.entity;

import javax.persistence.*;
import java.io.Serializable;

import com.lyc.simple.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author: liuyucai
 * @Created: 2023/04/14 08:50 
 * @Description:
 **/

@Data
@ToString
@Entity
@Table ( name ="oauth_service" )
public class OauthService extends BaseEntity<String> implements Serializable {

	private static final long serialVersionUID =  7768518034948319485L;

	/**
	 * id
	 */
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", nullable = false)
	private String id;

	/**
	 * 服务编码
	 */
   	@Column(name = "code")
	private String code;

	/**
	 * 名称
	 */
   	@Column(name = "name")
	private String name;

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
