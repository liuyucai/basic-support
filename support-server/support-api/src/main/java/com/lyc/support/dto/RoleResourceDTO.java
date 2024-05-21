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
 * @Created: 2023/05/13 15:16
 * @Description:
 **/

@Data
@ApiModel
public class RoleResourceDTO extends BaseDTO<String> implements Serializable {

	private static final long serialVersionUID =  6515709628217291011L;

	/**
	 * id
	 */
   	@ApiModelProperty("id")
	private String id;

	/**
	 * 角色id
	 */
   	@ApiModelProperty("角色id")
	private String roleId;

	/**
	 * 客户端id
	 */
   	@ApiModelProperty("客户端id")
	private String clientId;

	/**
	 * 客户端资源id
	 */
   	@ApiModelProperty("客户端资源id")
	private String resourceId;

	/**
	 * 客户端资源类型，MENU，ROUTER
	 */
   	@ApiModelProperty("客户端资源类型，MENU，ROUTER")
	private String resourceType;

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
