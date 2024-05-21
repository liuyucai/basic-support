package com.lyc.auth.entity.QO;

import com.lyc.simple.entity.PrimaryKeyEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/11/9 8:54
 * @Description:
 */
@Data
@ToString
@Entity
public class RoleApiQO extends PrimaryKeyEntity<String> implements Serializable {

    /**
     * id
     */
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    /**
     * serviceName
     */
    @Column(name = "service_name")
    private String serviceName;

    /**
     * url
     */
    @Column(name = "url")
    private String url;

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
