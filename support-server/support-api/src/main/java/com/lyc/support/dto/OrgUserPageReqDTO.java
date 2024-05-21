package com.lyc.support.dto;

import com.lyc.simple.annotation.Condition;
import com.lyc.simple.enmus.QueryType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/5/17 9:15
 * @Description:
 */
@Data
@ApiModel
public class OrgUserPageReqDTO implements Serializable {



    /**
     * 账号名称
     */
    @ApiModelProperty("账号id")
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
     * 账号名称
     */
    @ApiModelProperty("账号名称")
    private String userName;

    /**
     * 机构id
     */
    @ApiModelProperty("机构id")
    private String orgId;

    /**
     * 查找范围，1：本机构，2：本机构及子级
     */
    @ApiModelProperty("查找范围，1：本机构，2：本机构及子级")
    private String queryRang;


    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    @Condition(columnName = "phone_no",table = "sys_user_account",type = QueryType.LIKE)
    private String phoneNo;


}
