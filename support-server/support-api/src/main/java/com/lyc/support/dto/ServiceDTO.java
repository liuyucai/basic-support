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
 * @Created: 2023/04/14 08:50
 * @Description:
 **/

@Data
@ApiModel
public class ServiceDTO extends BaseDTO<String> implements Serializable {

	private static final long serialVersionUID =  2779302392293127762L;

	/**
	 * id
	 */
   	@ApiModelProperty("id")
	private String id;

	/**
	 * 服务编码
	 */
   	@ApiModelProperty("服务编码")
	private String code;

	/**
	 * 名称
	 */
   	@ApiModelProperty("名称")
	private String name;

	/**
	 * 描述
	 */
   	@ApiModelProperty("描述")
	private String description;

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
