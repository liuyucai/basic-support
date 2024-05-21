package com.lyc.support.entity.qo;

import com.lyc.simple.annotation.JoinColumn;
import com.lyc.simple.annotation.JoinTable;
import com.lyc.simple.entity.PrimaryKeyEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/5/11 8:44
 * @Description:
 */

@Data
@ToString
@Entity(name = "sys_role")
@JoinTable(name = "o",table = "sys_org",columnName = "org_id",referencedColumnName = "id")
public class RolePageRespQO extends PrimaryKeyEntity<String> implements Serializable {

    /**
     * id
     */
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    /**
     * 角色名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 所属机构
     */
    @Column(name = "org_id")
    private String orgId;

    /**
     * 应用范围，本级、本级及子级
     */
    @Column(name = "use_scope")
    private String useScope;

    /**
     * 数据权限
     */
    @Column(name = "data_scope")
    private String dataScope;

    /**
     * 角色类型，(基础角色=base,系统角色-system,业务角色=service，默认角色)
     */
    @Column(name = "type")
    private String type;

    /**
     * 排序
     */
    @Column(name = "sort")
    private Integer sort;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 机构名称
     */
    @Column(name = "org_name")
    @JoinColumn(name = "name",tableName = "o")
    private String orgName;

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
