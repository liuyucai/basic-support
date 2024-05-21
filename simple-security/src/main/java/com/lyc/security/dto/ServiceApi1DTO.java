package com.lyc.security.dto;

import java.io.Serializable;

import com.lyc.simple.dto.BaseDTO;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import java.util.Date;

/**
 * @author: liuyucai
 * @Created: 2023/07/29 16:36
 * @Description:
 **/

@Data
public class ServiceApi1DTO extends BaseDTO<String> implements Serializable {

	private static final long serialVersionUID =  3825954894673181533L;

	/**
	 * id
	 */
	private String id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 控制器
	 */
	private String handler;

	/**
	 * 路径
	 */
	private String url;

	/**
	 * 格式化路径
	 */
	private String formatUrl;

	/**
	 * 来源类型，SYSTEM：系统、INSERT：手动添加
	 */
	private String sourceType;

	/**
	 * 服务id
	 */
	private String serviceId;

	/**
	 * 请求类型
	 */
	private String requestMethod;

	/**
	 * 鉴权状态，1:鉴权，0：不鉴权
	 */
	private String authStatus;

	private String authSetting;

	/**
	 * 权限标识
	 */
	private String permission;

	/**
	 * 权限标识类型，CODE：代码添加、SYSTEM：系统自动生成、UPDATE：人工
	 */
	private String permissionType;

	/**
	 * 版本号
	 */
	private Integer version;

	/**
	 * 比对状态
	 */
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
