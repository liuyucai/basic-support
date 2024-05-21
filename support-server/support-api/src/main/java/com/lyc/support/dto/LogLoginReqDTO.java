package com.lyc.support.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: liuyucai
 * @Created: 2023/7/27 10:52
 * @Description:
 */
@Data
@ApiModel
public class LogLoginReqDTO implements Serializable {

    /**
     * 账号名称
     */
    @ApiModelProperty("账号名称")
    private String userName;

    /**
     * 登录状态
     */
    @ApiModelProperty("登录状态")
    private String status;

    /**
     * 成功状态
     */
    @ApiModelProperty("成功状态")
    private String state;


    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private Date endTime;

    /**
     * ip地址
     */
    @ApiModelProperty("ip地址")
    private String ipAddress;

    /**
     * 客户端id
     */
    @ApiModelProperty("客户端id")
    private String clientId;
}
