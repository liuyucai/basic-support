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
 * @Created: 2023/11/30 8:30
 * @Description:
 */
@Data
@ToString
@Entity
public class ClientAuthOrgUserQO extends PrimaryKeyEntity<String> implements Serializable {

    /**
     * id
     */
    @Id
    @Column(name = "id", nullable = false)
    private String id;


    /**
     * orgId
     */
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
     * 用户类型，1：主用户，0：普通用户
     */
    @Column(name = "type")
    private Integer type;

    /**
     * orgIndex
     */
    @Column(name = "org_index")
    private String orgIndex;

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
