package com.lyc.support.dto;

import java.io.Serializable;

import com.lyc.simple.dto.BaseDTO;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: liuyucai
 * @Created: 2023/08/25 14:31
 * @Description:
 **/

@Data
@ApiModel
public class ClientMenu1DTO extends BaseDTO<String> implements Serializable {

	private static final long serialVersionUID =  2229101637628870837L;

	/**
	 * id
	 */
   	@ApiModelProperty("id")
	private String id;

	/**
	 * 客户端id
	 */
   	@ApiModelProperty("客户端id")
	private String clientId;

	/**
	 * 上级菜单id
	 */
   	@ApiModelProperty("上级菜单id")
	private String pid;

	/**
	 * 菜单名称
	 */
   	@ApiModelProperty("菜单名称")
	private String name;

	/**
	 * 菜单类型，DIR：目录，MENU：菜单，ROUTER：路由，FUNCTION：功能
	 */
   	@ApiModelProperty("菜单类型，DIR：目录，MENU：菜单，ROUTER：路由，FUNCTION：功能")
	private String menuType;

	/**
	 * 路由地址
	 */
   	@ApiModelProperty("路由地址")
	private String path;

	/**
	 * 图标
	 */
   	@ApiModelProperty("图标")
	private String icon;

	/**
	 * 触发动作，路由/新开标签页
	 */
   	@ApiModelProperty("触发动作，路由/新开标签页")
	private String action;

	/**
	 * 是否显示
	 */
   	@ApiModelProperty("是否显示")
	private Integer visiable;

	/**
	 * 是否缓冲
	 */
   	@ApiModelProperty("是否缓冲")
	private Integer keepAlive;

	/**
	 * 权限标识
	 */
   	@ApiModelProperty("权限标识")
	private String permission;

	/**
	 * 是否鉴权
	 */
   	@ApiModelProperty("是否鉴权")
	private Integer authentication;

	/**
	 * 排序
	 */
   	@ApiModelProperty("排序")
	private Integer sort;

	/**
	 * 关联系列的id
	 */
   	@ApiModelProperty("关联系列的id")
	private String seriesIds;

    @JsonIgnore
	@Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
