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
 * @Created: 2023/09/03 16:52
 * @Description: 字典
 **/

@Data
@ApiModel
public class DictGroupDTO extends BaseDTO<String> implements Serializable {

	private static final long serialVersionUID =  7361520081183444323L;

	/**
	 * id
	 */
   	@ApiModelProperty("id")
	private String id;

	/**
	 * 字典名称
	 */
   	@ApiModelProperty("字典名称")
	private String name;

	/**
	 * 字典编码
	 */
   	@ApiModelProperty("字典编码")
	private String code;

	/**
	 * 字典类型，SYSTEM:系统类，BUSINESS
	 */
   	@ApiModelProperty("字典类型，SYSTEM:系统类，BUSINESS")
	private String type;

	/**
	 * 所属服务
	 */
   	@ApiModelProperty("所属服务")
	private String serviceId;

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
