package com.lyc.support.entity.qo;

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
 * @Created: 2023/5/7 17:04
 * @Description:
 */
@Data
@ToString
@Entity
@JoinTable(name = "o",table = "sys_org",columnName = "id",referencedColumnName = "id")
public class UserOrgQO extends PrimaryKeyEntity<String> implements Serializable {

    /**
     * 编号
     */
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    /**
     * 机构名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 上级组织id
     */
    @Column(name = "pid")
    private String pid;

    /**
     * 状态
     */
    @Column(name = "state")
    private Integer state;

    /**
     * 组织机构索引
     */
    @Column(name = "org_index")
    private String orgIndex;

    /**
     * 排序
     */
    @Column(name = "sort")
    private Integer sort;

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
