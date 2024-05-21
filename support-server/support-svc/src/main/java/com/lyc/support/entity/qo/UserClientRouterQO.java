package com.lyc.support.entity.qo;

import com.lyc.simple.annotation.JoinTable;
import com.lyc.simple.entity.PrimaryKeyEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/8/9 11:53
 * @Description:
 */
@Data
@ToString
@Entity
public class UserClientRouterQO extends PrimaryKeyEntity<String> implements Serializable {

    /**
     * id
     */
    @Id
    @Column(name = "id", nullable = false)
    private String id;

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
     * 角色资源id
     */
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
