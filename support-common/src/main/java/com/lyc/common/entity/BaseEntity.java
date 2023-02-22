package com.lyc.common.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: liuyucai
 * @Created: 2023/2/21 9:17
 * @Description:
 */
public abstract class BaseEntity<ID> implements Serializable {

    @Column(
            name = "deleted",
            nullable = false
    )
    private Integer deleted;
    @Column(
            name = "create_time",
            nullable = false
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @CreatedBy
    @Column(
            name = "create_user_id",
            nullable = true,
            length = 16
    )
    private String createUserId;
    @Column(
            name = "update_time",
            nullable = false
    )
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    @LastModifiedBy
    @Column(
            name = "update_user_id",
            nullable = true,
            length = 16
    )
    private String updateUserId;

    public BaseEntity() {
    }

    public abstract ID getPrimaryKey();

    public abstract void setPrimaryKey(ID var1);

    public Integer getDeleted() {
        return this.deleted;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public String getCreateUserId() {
        return this.createUserId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public String getUpdateUserId() {
        return this.updateUserId;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }
}
