package com.lyc.support.entity.qo;

import com.lyc.simple.annotation.*;
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
 * @Created: 2023/9/2 22:53
 * @Description:
 */
@Data
@ToString
@Entity
@JoinTable(name = "a",table = "oauth_service_auth",columnName = "id",referencedColumnName = "service_id")
public class ServiceAuthRespQO extends PrimaryKeyEntity<String> implements Serializable {

    /**
     * id
     */
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    /**
     * 服务编码
     */
    @Column(name = "code")
    private String code;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 授权id
     */
    @Column(name = "auth_id")
    @JoinColumn(name = "id",tableName = "a")
    private String authId;


    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
