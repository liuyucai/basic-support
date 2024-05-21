package com.lyc.support.entity.qo;

import com.lyc.simple.entity.PrimaryKeyEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/5/13 16:40
 * @Description:
 */
@Data
@ToString
@Entity
public class RoleClientQO extends PrimaryKeyEntity<String> implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private String id;


    /**
     * 客户端名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 图标
     */
    @Column(name = "icon")
    private String icon;

    @Column(name = "role_resource_id")
    private String roleResourceId;

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
