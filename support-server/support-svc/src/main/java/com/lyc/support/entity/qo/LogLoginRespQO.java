package com.lyc.support.entity.qo;

import com.lyc.simple.entity.PrimaryKeyEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: liuyucai
 * @Created: 2023/7/27 10:47
 * @Description:
 */
@Data
@ToString
@Entity
public class LogLoginRespQO extends PrimaryKeyEntity<String> implements Serializable {

    /**
     * id
     */
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    /**
     * 账号名称
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 机构用户id
     */
    @Column(name = "org_user_id")
    private String orgUserId;

    /**
     * 登录时间
     */
    @Column(name = "login_time")
    private Date loginTime;

    /**
     * 浏览器
     */
    @Column(name = "browser")
    private String browser;

    /**
     * 操作系统
     */
    @Column(name = "os")
    private String os;

    /**
     * ip地址
     */
    @Column(name = "ip_address")
    private String ipAddress;

    /**
     * ip所在位置
     */
    @Column(name = "ip_location")
    private String ipLocation;

    /**
     * 登录信息
     */
    @Column(name = "login_info")
    private String loginInfo;

    /**
     * 登录状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 描述
     */
    @Column(name = "msg")
    private String msg;

    /**
     * 客户端id
     */
    @Column(name = "client_id")
    private String clientId;

    /**
     * 用户昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 客户端名称
     */
    @Column(name = "client_name")
    private String clientName;

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
