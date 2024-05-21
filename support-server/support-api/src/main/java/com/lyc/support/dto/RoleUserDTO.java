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
 * @Created: 2023/5/20 16:46
 * @Description:
 */

@Data
@ApiModel
public class RoleUserDTO extends BaseDTO<String> implements Serializable {

	private static final long serialVersionUID =  8137451140380270325L;

	/**
	 * id
	 */
   	@ApiModelProperty("id")
	private String id;

	/**
	 * 用户id
	 */
   	@ApiModelProperty("用户id")
	private String orgUserId;

	/**
	 * 角色id
	 */
   	@ApiModelProperty("角色id")
	private String roleId;

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
