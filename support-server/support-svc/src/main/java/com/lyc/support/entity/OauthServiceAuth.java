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
 * @Created: 2023/09/02 21:18 
 * @Description: 服务授权
 **/

@Data
@ToString
@Entity
@Table ( name ="oauth_service_auth" )
public class OauthServiceAuth extends BaseEntity<String> implements Serializable {

	private static final long serialVersionUID =  4333928626454941858L;

	/**
	 * id
	 */
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", nullable = false)
	private String id;

	/**
	 * 服务id
	 */
   	@Column(name = "service_id")
	private String serviceId;

	/**
	 * 授权服务id
	 */
   	@Column(name = "auth_service_id")
	private String authServiceId;

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
