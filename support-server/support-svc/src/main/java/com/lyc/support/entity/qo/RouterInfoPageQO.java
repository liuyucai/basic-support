package com.lyc.support.entity.qo;

import com.lyc.simple.annotation.JoinColumn;
import com.lyc.simple.annotation.JoinTable;
import com.lyc.simple.entity.PrimaryKeyEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/4/27 8:56
 * @Description:
 */
@Data
@ToString
@Entity(name = "oauth_client_router")
@JoinTable(name = "f",nativeSql = " select pid,count(1) as function_number from oauth_client_router where deleted = 0 group by pid",columnName = "id",referencedColumnName = "pid",autoFilterDeleted = false)
public class RouterInfoPageQO extends PrimaryKeyEntity<String> implements Serializable {

    /**
     * id
     */
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    /**
     * 客户端id
     */
    @Column(name = "client_id")
    private String clientId;

    /**
     * 路由id
     */
    @Column(name = "pid")
    private String pid;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 路由地址
     */
    @Column(name = "path")
    private String path;

    /**
     * 类型
     */
    @Column(name = "type")
    private String type;


    /**
     * 是否鉴权
     */
    @Column(name = "authentication")
    private Integer authentication;

    /**
     * 排序
     */
    @Column(name = "sort")
    private Integer sort;

    /**
     * 功能数量
     */
    @Column(name = "function_number")
    @JoinColumn(name = "function_number",tableName = "f")
    private Integer functionNumber;



    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
