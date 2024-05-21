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
 * @Created: 2023/5/22 8:58
 * @Description:
 */
@Data
@ToString
@Entity
public class OrgUserDetailQO extends PrimaryKeyEntity<String> implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "org_id")
    private String orgId;

    @Column(name = "org_name")
    private String orgName;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

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

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
