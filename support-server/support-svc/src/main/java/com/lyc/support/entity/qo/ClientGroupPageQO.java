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
 * @Created: 2023/4/11 9:08
 * @Description:
 */
@Data
@ToString
@Entity(name = "oauth_client_group")
@JoinTable(name = "c",nativeSql = " select group_id,count(1) as client_number from oauth_client where deleted = 0 group by group_id",columnName = "id",referencedColumnName = "group_id",autoFilterDeleted=false)
public class ClientGroupPageQO extends PrimaryKeyEntity<String> implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;


    /**
     * 图标
     */
    @Column(name = "icon")
    private String icon;

    /**
     * 排序
     */
    @Column(name = "sort")
    private Long sort;

    /**
     * 客户端数量
     */
    @Column(name = "client_number")
    @JoinColumn(name = "client_number",tableName = "c")
    private Integer clientNumber;

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id=id;
    }
}
