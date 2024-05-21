package com.lyc.support.dto;

import java.io.Serializable;

import com.lyc.simple.dto.BaseDTO;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * @author: liuyucai
 * @Created: 2023/04/23 18:20
 * @Description:
 **/

@Data
@ApiModel
public class ClientRouterDTO extends BaseDTO<String> implements Serializable {

	private static final long serialVersionUID =  5194214250049676473L;

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
	 * 路由id
	 */
	@ApiModelProperty("路由id")
	private String pid;

	/**
	 * 名称
	 */
   	@ApiModelProperty("名称")
	private String name;

	/**
	 * 路由地址
	 */
   	@ApiModelProperty("路由地址")
	private String path;

	/**
	 * 类型
	 */
   	@ApiModelProperty("类型")
	private String type;

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
