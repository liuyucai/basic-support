package com.lyc.support.entity.qo;

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
 * @Created: 2023/6/14 22:23
 * @Description:
 */
@Data
@ToString
@Entity
public class UserClientMenuQO extends PrimaryKeyEntity<String> implements Serializable {

    /**
     * id
     */
    @Id
    @Column(name = "id", nullable = false)
    private String id;


    /**
     * 上级菜单id
     */
    @Column(name = "pid")
    private String pid;

    /**
     * 菜单名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 菜单类型  1:目录，2:菜单
     */
    @Column(name = "menu_type")
    private String menuType;

    /**
     * 路由地址
     */
    @Column(name = "path")
    private String path;

    /**
     * 图标
     */
    @Column(name = "icon")
    private String icon;

    /**
     * 触发动作，路由/新开标签页
     */
    @Column(name = "action")
    private String action;

    /**
     * 是否显示
     */
    @Column(name = "visiable")
    private Integer visiable;

    /**
     * 是否缓冲
     */
    @Column(name = "keep_alive")
    private Integer keepAlive;

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
