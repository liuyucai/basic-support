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
import java.util.Date;

/**
 * @author: liuyucai
 * @Created: 2023/5/9 9:17
 * @Description:
 */
@Data
@ToString
@Entity(name = "sys_user_account")
public class UserAccountPageRespQO extends PrimaryKeyEntity<String> implements Serializable {

    /**
     * 用户ID
     */
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 真实姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 证件类型
     */
    @Column(name = "identity_type")
    private String identityType;

    /**
     * 证件号码
     */
    @Column(name = "identity_no")
    private String identityNo;

    /**
     * 出生日期
     */
    @Column(name = "birthday")
    private Date birthday;

    /**
     * 性别
     */
    @Column(name = "gender")
    private String gender;

    /**
     * 手机号码
     */
    @Column(name = "phone_no")
    private String phoneNo;

    /**
     * 头像
     */
    @Column(name = "avatar")
    private String avatar;


    /**
     * 账号是否有效，1：有效，2：禁用
     */
    @Column(name = "enabled")
    private Integer enabled;


    /**
     * 账号类型，TEMP：临时账号，COMMON：普通账号
     */
    @Column(name = "account_type")
    private String accountType;

    /**
     * 有效截止时间
     */
    @Column(name = "effective_deadline")
    private Date effectiveDeadline;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
