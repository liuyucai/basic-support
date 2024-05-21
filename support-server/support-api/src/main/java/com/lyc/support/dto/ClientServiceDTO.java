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
 * @Created: 2023/04/17 08:45
 * @Description:
 **/

@Data
@ApiModel
public class ClientServiceDTO extends BaseDTO<String> implements Serializable {

	private static final long serialVersionUID =  7602688659312890711L;

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
	 * 服务id
	 */
   	@ApiModelProperty("服务id")
	private String serviceId;

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
