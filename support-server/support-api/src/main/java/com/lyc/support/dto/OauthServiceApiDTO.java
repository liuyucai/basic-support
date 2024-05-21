package com.lyc.support.dto;

import java.io.Serializable;

import com.lyc.simple.annotation.Like;
import com.lyc.simple.dto.BaseDTO;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

/**
 * @author: liuyucai
 * @Created: 2023/07/30 12:30
 * @Description:
 **/

@Data
@ApiModel
public class OauthServiceApiDTO extends BaseDTO<String> implements Serializable {

	private static final long serialVersionUID =  698121216210570453L;

	/**
	 * id
	 */
   	@ApiModelProperty("id")
	private String id;

	/**
	 * 名称
	 */
   	@ApiModelProperty("名称")
	@Like
	private String name;

	/**
	 * 控制器
	 */
   	@ApiModelProperty("控制器")
	private String handler;

	/**
	 * 路径
	 */
   	@ApiModelProperty("路径")
	@Like
	private String url;


	/**
	 * 格式化路径
	 */
	@ApiModelProperty("格式化路径")
	private String formatUrl;

	/**
	 * 来源类型，SYSTEM：系统、INSERT：手动添加
	 */
   	@ApiModelProperty("来源类型，SYSTEM：系统、INSERT：手动添加")
	private String sourceType;

	/**
	 * 服务id
	 */
   	@ApiModelProperty("服务id")
	private String serviceId;

	/**
	 * 请求类型
	 */
   	@ApiModelProperty("请求类型")
	private String requestMethod;

	/**
	 * 鉴权状态，1:鉴权，0：不鉴权
	 */
   	@ApiModelProperty("鉴权状态，1:鉴权，0：不鉴权")
	private String authStatus;

   	@ApiModelProperty("权限设置，SYSTEM: 系统、UPDATE: 人工")
	private String authSetting;

	@ApiModelProperty("权限标识")
	private String permission;

	/**
	 * 权限标识类型，CODE：代码添加、SYSTEM：系统自动生成、UPDATE：人工
	 */
	private String permissionType;

	/**
	 * 版本号
	 */
   	@ApiModelProperty("版本号")
	private Integer version;

	/**
	 * 比对状态
	 */
	@ApiModelProperty("比对状态")
	private String compareStatus;

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
