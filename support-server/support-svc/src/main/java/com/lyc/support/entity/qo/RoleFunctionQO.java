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
 * @Created: 2023/5/14 15:06
 * @Description:
 */
@Data
@ToString
@Entity
public class RoleFunctionQO extends PrimaryKeyEntity<String> implements Serializable {

    /**
     * id
     */
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    /**
     * 客户端id
     */
    @Column(name = "client_id")
    private String clientId;

    /**
     * 路由id
     */
    @Column(name = "pid")
    private String pid;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 路由地址
     */
    @Column(name = "path")
    private String path;

    /**
     * 类型
     */
    @Column(name = "type")
    private String type;

    /**
     * 权限标识
     */
    @Column(name = "permission")
    private String permission;

    /**
     * 是否鉴权
     */
    @Column(name = "authentication")
    private Integer authentication;

    /**
     * 排序
     */
    @Column(name = "sort")
    private Integer sort;

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
