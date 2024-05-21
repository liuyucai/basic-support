package com.lyc.support.dto;

import java.io.Serializable;

import com.lyc.simple.dto.BaseDTO;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: liuyucai
 * @Created: 2023/05/28 15:11
 * @Description:
 **/

@Data
@ApiModel
public class RoleDisabledDTO extends BaseDTO<String> implements Serializable {

	private static final long serialVersionUID =  8134202542333067526L;

	/**
	 * id
	 */
   	@ApiModelProperty("id")
	private String id;

	/**
	 * 机构用户id
	 */
   	@ApiModelProperty("机构用户id")
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
