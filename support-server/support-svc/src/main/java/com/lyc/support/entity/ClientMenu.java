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
 * @Created: 2023/04/20 16:29 
 * @Description:
 **/

@Data
@ToString
@Entity
@Table ( name ="oauth_client_menu" )
public class ClientMenu extends BaseEntity<String> implements Serializable {

	private static final long serialVersionUID =  5583618142036469973L;

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
	 * 上级菜单id
	 */
   	@Column(name = "pid")
	private String pid;

	/**
	 * 菜单名称
	 */
   	@Column(name = "name")
	private String name;

	/**
	 * 菜单类型  1:目录，2:菜单
	 */
	@Column(name = "menu_type")
	private String menuType;

	/**
	 * 路由地址
	 */
   	@Column(name = "path")
	private String path;

	/**
	 * 图标
	 */
   	@Column(name = "icon")
	private String icon;

	/**
	 * 触发动作，路由/新开标签页
	 */
   	@Column(name = "action")
	private String action;

	/**
	 * 是否显示
	 */
   	@Column(name = "visiable")
	private Integer visiable;

	/**
	 * 是否缓冲
	 */
   	@Column(name = "keep_alive")
	private Integer keepAlive;

	/**
	 * 排序
	 */
   	@Column(name = "sort")
	private Integer sort;

	/**
	 * 关联系列的id
	 */
	@Column(name = "series_ids")
	private String seriesIds;

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
