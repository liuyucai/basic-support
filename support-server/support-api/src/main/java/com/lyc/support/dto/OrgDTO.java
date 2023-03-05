package com.lyc.support.dto;

import java.io.Serializable;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * @author 
 * @version Id: SysOrgController.java, v 0.1 2023/03/05 18:35  Exp $$
 */

@Data
@ApiModel
public class OrgDTO implements Serializable {

	private static final long serialVersionUID =  104419954541508291L;

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
	 * 组织机构索引
	 */
   	@ApiModelProperty("组织机构索引")
	private String orgIndex;

	/**
	 * 排序
	 */
   	@ApiModelProperty("排序")
	private Integer sort;

}
