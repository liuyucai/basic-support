package com.lyc.security.entity;

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
 * @Created: 2023/09/01 21:29 
 * @Description: */

@Data
@ToString
@Entity
@Table ( name ="oauth_client" )
public class OauthClient1 extends BaseEntity<String> implements Serializable {

	private static final long serialVersionUID =  2082821801932892860L;

	/**
	 * id
	 */
    @Id
    @GenericGenerator(name = "jpa-uuid", strategy = "uuid")
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "id", nullable = false)
	private String id;

	/**
	 * 客户端分组id
	 */
   	@Column(name = "group_id")
	private String groupId;

	/**
	 * 客户端名称
	 */
   	@Column(name = "name")
	private String name;

	/**
	 * 图标
	 */
   	@Column(name = "icon")
	private String icon;

	/**
	 * 应用场景，pc、移动
	 */
   	@Column(name = "app_scene")
	private String appScene;

	/**
	 * 应用来源
	 */
   	@Column(name = "app_source")
	private String appSource;

	/**
	 * 客户端密钥
	 */
   	@Column(name = "client_secret")
	private String clientSecret;

	/**
	 * 鉴权类型
	 */
   	@Column(name = "grant_type")
	private String grantType;

	/**
	 * 客户端入口
	 */
   	@Column(name = "redirect_uri")
	private String redirectUri;

	/**
	 * 状态，上架、下架
	 */
   	@Column(name = "state")
	private String state;

	/**
	 * 是否匿名访问
	 */
   	@Column(name = "anonymous")
	private Integer anonymous;

	/**
	 * access token有效时间(分钟)
	 */
   	@Column(name = "access_token_validity")
	private Long accessTokenValidity;

	/**
	 * 客户端描述
	 */
   	@Column(name = "description")
	private String description;

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
