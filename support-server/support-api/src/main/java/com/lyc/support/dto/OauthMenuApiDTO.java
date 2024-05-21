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
 * @Created: 2023/11/01 08:46
 * @Description:
 **/

@Data
@ApiModel
public class OauthMenuApiDTO extends BaseDTO<String> implements Serializable {

	private static final long serialVersionUID =  3315099503507545965L;

	/**
	 * id
	 */
   	@ApiModelProperty("id")
	private String id;

	/**
	 * 菜单id
	 */
   	@ApiModelProperty("菜单id")
	private String menuId;

	/**
	 * 接口id
	 */
   	@ApiModelProperty("接口id")
	private String apiId;

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
