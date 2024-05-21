package com.lyc.support.entity;

import javax.persistence.*;
import java.io.Serializable;

import com.lyc.simple.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author: liuyucai
 * @Created: 2023/11/01 08:46 
 * @Description:
 **/

@Data
@ToString
@Entity
@Table ( name ="oauth_menu_api" )
public class OauthMenuApi extends BaseEntity<String> implements Serializable {

	private static final long serialVersionUID =  5690917123126841200L;

	/**
	 * id
	 */
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", nullable = false)
	private String id;

	/**
	 * 菜单id
	 */
   	@Column(name = "menu_id")
	private String menuId;

	/**
	 * 接口id
	 */
   	@Column(name = "api_id")
	private String apiId;

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
