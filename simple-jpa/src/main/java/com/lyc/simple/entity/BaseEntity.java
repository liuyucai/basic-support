package com.lyc.simple.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: liuyucai
 * @Created: 2023/2/21 9:17
 * @Description:
 *
 * 1.@MappedSuperclass注解只能标准在类上：@Target({java.lang.annotation.ElementType.TYPE})
 *
 * 2.标注为@MappedSuperclass的类将不是一个完整的实体类，他将不会映射到数据库表，但是他的属性都将映射到其子类的数据库字段中。
 *
 * 3.标注为@MappedSuperclass的类不能再标注@Entity或@Table注解，也无需实现序列化接口。
 *
 * @EntityListeners 注解继承了@Target注解
 */
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class BaseEntity<ID> extends PrimaryKeyEntity<ID> implements Serializable {

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

    @Override
    public abstract ID getPrimaryKey();

    @Override
    public abstract void setPrimaryKey(ID id);

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
