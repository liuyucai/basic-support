package com.lyc.auth.dto;

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
 * @Created: 2023/7/24 10:58
 * @Description:
 */

@Data
@ApiModel
public class LogLoginDTO extends BaseDTO<String> implements Serializable {

	private static final long serialVersionUID =  5761571461528688086L;

	/**
	 * id
	 */
   	@ApiModelProperty("id")
	private String id;

	/**
	 * 账号名称
	 */
   	@ApiModelProperty("账号名称")
	private String userName;

	/**
	 * 机构用户id
	 */
   	@ApiModelProperty("机构用户id")
	private String orgUserId;

	/**
	 * 登录时间
	 */
   	@ApiModelProperty("登录时间")
	private Date loginTime;

	/**
	 * 浏览器
	 */
   	@ApiModelProperty("浏览器")
	private String browser;

	/**
	 * 操作系统
	 */
   	@ApiModelProperty("操作系统")
	private String os;

	/**
	 * ip地址
	 */
   	@ApiModelProperty("ip地址")
	private String ipAddress;

	/**
	 * ip所在位置
	 */
   	@ApiModelProperty("ip所在位置")
	private String ipLocation;

	/**
	 * 登录信息
	 */
   	@ApiModelProperty("登录信息")
	private String loginInfo;

	/**
	 * 登录状态
	 */
	@ApiModelProperty("登录状态")
	private String status;

	/**
	 * 描述
	 */
	@ApiModelProperty("描述")
	private String msg;

	/**
	 * 客户端id
	 */
	@ApiModelProperty("客户端id")
	private String clientId;

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
