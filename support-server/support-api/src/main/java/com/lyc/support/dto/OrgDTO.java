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
 * @Created: 2023/04/28 22:35
 * @Description:
 **/

@Data
@ApiModel
public class OrgDTO extends BaseDTO<String> implements Serializable {

	private static final long serialVersionUID =  7111282181578766020L;

	/**
	 * 编号
	 */
   	@ApiModelProperty("编号")
	private String id;

	/**
	 * 机构名称
	 */
   	@ApiModelProperty("机构名称")
	private String name;

	/**
	 * 上级组织id
	 */
   	@ApiModelProperty("上级组织id")
	private String pid;

	/**
	 * 状态
	 */
   	@ApiModelProperty("状态")
	private Integer state;

	/**
	 * 组织机构索引
	 */
   	@ApiModelProperty("组织机构索引")
	private String orgIndex;

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
