package com.lyc.support.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: liuyucai
 * @Created: 2023/7/27 10:50
 * @Description:
 */
@Data
@ApiModel
public class LogLoginRespDTO implements Serializable {

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickName;

    /**
     * 客户端名称
     */
    @ApiModelProperty("客户端名称")
    private String clientName;
}
