package com.example.graphql.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


/**
 * @author pcs
 * Date         2020/4/9
 * Description:
 */
@Entity(name = "author")
@Data
public class Author {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", columnDefinition = "BIGINT", nullable = false)
    private Long id;

    /**
     * 创建时间戳 (单位:秒)
     */
    @Column(name = "CREATED_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    /**
     * 更新时间戳 (单位:秒)
     */
    @Column(name = "UPDATED_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    @Column(name = "FIRST_NAME", columnDefinition = "varchar(50)")
    private String firstName;

    @Column(name = "LAST_NAME", columnDefinition = "varchar(50)")
    private String lastName;

    public Author() {
        createdTime = new Date();
        updatedTime = createdTime;
    }

    @PreUpdate
    private void doPreUpdate() {
        updatedTime = new Date();
    }
}
