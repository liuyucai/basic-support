package com.lyc.support.entity.qo;

import com.lyc.simple.annotation.*;
import com.lyc.simple.entity.PrimaryKeyEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author: liuyucai
 * @Created: 2023/3/27 11:11
 * @Description:
 */
@Data
@ToString
@Entity(name = "oauth_client")
@JoinTable(name = "g",table = "oauth_client_group",columnName = "group_id",referencedColumnName = "id")
@JoinTables({
        @JoinTable(name = "g",table = "oauth_client_group",columnName = "group_id",referencedColumnName = "id"),
        @JoinTable(name = "g",table = "oauth_client_group",columnName = "group_id",referencedColumnName = "id")
})
public class TestQO extends PrimaryKeyEntity<String> implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "group_name", nullable = false)
    @JoinColumn(name = "name",tableName = "g")
    private String groupName;

    @Override
    public String getPrimaryKey() {
        return this.id;
    }

    @Override
    public void setPrimaryKey(String id) {
        this.id = id;
    }
}
