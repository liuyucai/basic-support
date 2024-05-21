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
 * @Created: 2023/11/1 9:26
 * @Description:
 */
@Data
@ToString
@Entity
public class OauthMenuApiQO extends PrimaryKeyEntity<String> implements Serializable {

    /**
     * id
     */
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;


    /**
     * 路径
     */
    @Column(name = "url")
    private String url;


    /**
     * 来源类型，SYSTEM：系统、INSERT：手动添加
     */
    @Column(name = "source_type")
    private String sourceType;

    /**
     * 服务id
     */
    @Column(name = "service_id")
    private String serviceId;

    /**
     * 请求类型
     */
    @Column(name = "request_method")
    private String requestMethod;

    /**
     * 鉴权状态，1:鉴权，0：不鉴权
     */
    @Column(name = "auth_status")
    private String authStatus;

    /**
     * menuApiId
     */
    @Column(name = "menu_api_id")
    private String menuApiId;


    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
