package com.lyc.support.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lyc.simple.annotation.JoinColumn;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: liuyucai
 * @Created: 2023/5/17 9:02
 * @Description:
 */
@Data
@ApiModel
public class OrgUserPageRespDTO implements Serializable {


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
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

    /**
     * 账号名称
     */
    @ApiModelProperty("账号名称")
    private String userName;


    /**
     * 真实姓名
     */
    @ApiModelProperty("真实姓名")
    private String realName;

    /**
     * 性别
     */
    @ApiModelProperty("性别")
    private String gender;


    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String phoneNo;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String avatar;


    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 用户类型
     */
    @ApiModelProperty("用户类型")
    private String type;

    /**
     * 机构名称
     */
    @ApiModelProperty("机构名称")
    private String orgName;


}
