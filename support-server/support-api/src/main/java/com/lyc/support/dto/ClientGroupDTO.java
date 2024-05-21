package com.lyc.support.dto;

import java.io.Serializable;

import com.lyc.simple.dto.BaseDTO;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * @author liuyucai
 *
 */

@Data
@ApiModel
public class ClientGroupDTO extends BaseDTO<String> implements Serializable {

	private static final long serialVersionUID =  9052382744677576472L;

	/**
	 * 主键
	 */
   	@ApiModelProperty("主键")
	private String id;

	/**
	 * 名称
	 */
   	@ApiModelProperty("名称")
	private String name;

	/**
	 * 图标
	 */
   	@ApiModelProperty("图标")
	private String icon;

	/**
	 * 排序
	 */
   	@ApiModelProperty("排序")
	private Long sort;

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
