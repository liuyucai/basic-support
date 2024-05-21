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
 * @Created: 2023/04/29 13:40
 * @Description:
 **/

@Data
@ApiModel
public class RoleDTO extends BaseDTO<String> implements Serializable {

	private static final long serialVersionUID =  6034845268472916940L;

	/**
	 * id
	 */
   	@ApiModelProperty("id")
	private String id;

	/**
	 * 角色名称
	 */
   	@ApiModelProperty("角色名称")
	private String name;

	/**
	 * 所属机构
	 */
   	@ApiModelProperty("所属机构")
	private String orgId;

	/**
	 * 应用范围，本级、本级及子级
	 */
   	@ApiModelProperty("使用范围，本级、本级及子级")
	private String useScope;

	/**
	 * 数据权限
	 */
   	@ApiModelProperty("数据权限")
	private String dataScope;

	/**
	 * 角色类型，(基础角色=base,系统角色-system,业务角色=service，默认角色)
	 */
   	@ApiModelProperty("角色类型，(基础角色=base,系统角色-system,业务角色=service，默认角色)")
	private String type;

	/**
	 * 排序
	 */
   	@ApiModelProperty("排序")
	private Integer sort;

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
