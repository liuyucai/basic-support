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
 * @Created: 2023/09/02 21:18
 * @Description: 服务授权
 **/

@Data
@ApiModel
public class OauthServiceAuthDTO extends BaseDTO<String> implements Serializable {

	private static final long serialVersionUID =  6964268906918190926L;

	/**
	 * id
	 */
   	@ApiModelProperty("id")
	private String id;

	/**
	 * 服务id
	 */
   	@ApiModelProperty("服务id")
	private String serviceId;

	/**
	 * 授权服务id
	 */
   	@ApiModelProperty("授权服务id")
	private String authServiceId;

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
