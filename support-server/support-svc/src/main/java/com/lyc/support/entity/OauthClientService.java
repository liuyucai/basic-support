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
 * @Created: 2023/04/17 08:45 
 * @Description:
 **/

@Data
@ToString
@Entity
@Table ( name ="oauth_client_service" )
public class OauthClientService extends BaseEntity<String> implements Serializable {

	private static final long serialVersionUID =  2887205860736056133L;

	/**
	 * id
	 */
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", nullable = false)
	private String id;

	/**
	 * 客户端id
	 */
   	@Column(name = "client_id")
	private String clientId;

	/**
	 * 服务id
	 */
   	@Column(name = "service_id")
	private String serviceId;

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
