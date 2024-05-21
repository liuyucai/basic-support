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
 * @Created: 2023/3/26 13:54
 * @Description:
 */
@Data
@ToString
@Entity
public class ClientPageQO extends PrimaryKeyEntity<String> implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    /**
     * 客户端分组id
     */
    @Column(name = "group_id")
    private String groupId;

    @Column(name = "group_name")
    private String groupName;

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

    /**
     * 应用场景，pc、移动
     */
    @Column(name = "app_scene")
    private String appScene;

    /**
     * 应用来源
     */
    @Column(name = "app_source")
    private String appSource;

    /**
     * 客户端密钥
     */
    @Column(name = "client_secret")
    private String clientSecret;

    /**
     * 鉴权类型
     */
    @Column(name = "grant_type")
    private String grantType;

    /**
     * 状态，上架、下架
     */
    @Column(name = "state")
    private String state;

    /**
     * 是否匿名访问
     */
    @Column(name = "anonymous")
    private Integer anonymous;

    /**
     * access token有效时间(分钟)
     */
    @Column(name = "access_token_validity")
    private Long accessTokenValidity;

    /**
     * 客户端描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 客户端入口
     */
    @Column(name = "redirect_uri")
    private String redirectUri;

    /**
     * 排序
     */
    @Column(name = "sort")
    private Long sort;

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }

}
