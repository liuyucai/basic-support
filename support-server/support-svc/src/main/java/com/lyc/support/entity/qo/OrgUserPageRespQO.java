package com.lyc.support.entity.qo;

import com.lyc.simple.annotation.JoinColumn;
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
import java.util.Date;

/**
 * @author: liuyucai
 * @Created: 2023/5/17 14:11
 * @Description:
 */
@Data
@ToString
@Entity(name = "sys_org_user")
@JoinTable(name = "u",table = "sys_user_account",columnName = "user_id",referencedColumnName = "id")
@JoinTable(name = "o",table = "sys_org",columnName = "org_id",referencedColumnName = "id",autoFilterDeleted=false)
public class OrgUserPageRespQO extends PrimaryKeyEntity<String> implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "org_id")
    private String orgId;

    @Column(name = "user_id")
    private String userId;

    /**
     * 用户昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 账号是否有效，1：有效，0：禁用
     */
    @Column(name = "enabled")
    private Integer enabled;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 用户类型
     */
    @Column(name = "type")
    private String type;

    /**
     * 用户账号
     */
    @Column(name = "user_name")
    @JoinColumn(name = "user_name",tableName = "u")
    private String userName;

    /**
     * 真实姓名
     */
    @Column(name = "real_name")
    @JoinColumn(name = "real_name",tableName = "u")
    private String realName;

    /**
     * 性别
     */
    @Column(name = "gender")
    @JoinColumn(name = "gender",tableName = "u")
    private String gender;

    /**
     * 手机号码
     */
    @Column(name = "phone_no")
    @JoinColumn(name = "phone_no",tableName = "u")
    private String phoneNo;

    /**
     * 头像
     */
    @Column(name = "avatar")
    @JoinColumn(name = "avatar",tableName = "u")
    private String avatar;


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
