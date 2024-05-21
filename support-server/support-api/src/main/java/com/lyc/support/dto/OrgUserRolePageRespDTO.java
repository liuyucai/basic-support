package com.lyc.support.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/5/25 10:53
 * @Description:
 */
@Data
@ApiModel
public class OrgUserRolePageRespDTO implements Serializable {

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
     * 所属机构名称
     */
    @ApiModelProperty("所属机构名称")
    private String orgName;

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

    /**
     * disabledId
     */
    @ApiModelProperty("disabledId")
    private String disabledId;


    /**
     * 用户角色id
     */
    @ApiModelProperty("roleUserId")
    private String roleUserId;
}
