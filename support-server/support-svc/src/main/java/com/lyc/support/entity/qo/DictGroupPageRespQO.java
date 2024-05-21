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

/**
 * @author: liuyucai
 * @Created: 2023/9/3 17:33
 * @Description:
 */
@Data
@ToString
@Entity
@JoinTable(name = "s",table = "oauth_service",columnName = "service_id",referencedColumnName = "id")
public class DictGroupPageRespQO extends PrimaryKeyEntity<String> implements Serializable {

    /**
     * id
     */
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    /**
     * 字典名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 字典编码
     */
    @Column(name = "code")
    private String code;

    /**
     * 字典类型，SYSTEM:系统类，BUSINESS
     */
    @Column(name = "type")
    private String type;

    /**
     * 所属服务
     */
    @Column(name = "service_id")
    private String serviceId;

    /**
     * 所属服务
     */
    @Column(name = "service_name")
    @JoinColumn(tableName = "s",name = "name")
    private String serviceName;

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
