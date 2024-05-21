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
 * @Created: 2023/05/16 16:58
 * @Description:
 **/

@Data
@ApiModel
public class OrgUserDTO extends BaseDTO<String> implements Serializable {

	private static final long serialVersionUID =  967123136186571644L;

   	@ApiModelProperty("id")
	private String id;

   	@ApiModelProperty("机构id")
	private String orgId;

   	@ApiModelProperty("用户账号id")
	private String userId;

	/**
	 * 用户昵称
	 */
   	@ApiModelProperty("用户昵称")
	private String nickName;

	/**
	 * 账号是否有效，1：有效，0：禁用
	 */
   	@ApiModelProperty("账号是否有效，1：有效，0：禁用")
	private Integer enabled;

	/**
	 * 用户类型，1：主用户，0：普通用户
	 */
	@ApiModelProperty("用户类型")
	private Integer type;

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
