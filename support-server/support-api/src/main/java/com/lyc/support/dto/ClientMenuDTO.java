package com.lyc.support.dto;

import java.io.Serializable;

import com.lyc.simple.dto.BaseDTO;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.util.Date;

/**
 * @author: liuyucai
 * @Created: 2023/04/20 16:29
 * @Description:
 **/

@Data
@ApiModel
public class ClientMenuDTO extends BaseDTO<String> implements Serializable {

	private static final long serialVersionUID =  378361431826292614L;

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
	 * 菜单类型  1:目录，2:菜单
	 */
	@ApiModelProperty("菜单类型")
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
