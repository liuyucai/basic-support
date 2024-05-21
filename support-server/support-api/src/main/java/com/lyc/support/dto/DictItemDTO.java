package com.lyc.support.dto;

import java.io.Serializable;

import com.lyc.simple.dto.BaseDTO;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.util.Date;

/**
 * @author: liuyucai
 * @Created: 2023/09/03 16:54
 * @Description: 字典项
 **/

@Data
@ApiModel
public class DictItemDTO extends BaseDTO<String> implements Serializable {

	private static final long serialVersionUID =  504278179213744286L;

	/**
	 * id
	 */
   	@ApiModelProperty("id")
	private String id;

	/**
	 * 名称
	 */
   	@ApiModelProperty("名称")
	private String name;

	/**
	 * 编码
	 */
   	@ApiModelProperty("编码")
	private String code;

	/**
	 * 字典组id
	 */
	@ApiModelProperty("字典组id")
	private String groupId;

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
