package com.example.graphql.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author pcs
 * Date         2020/4/9
 * Description:
 */
@Entity(name = "book")
@Data
public class Book {

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
    @Column(name = "title", columnDefinition = "varchar(50)")
    private String title;
    @Column(name = "isbn", columnDefinition = "varchar(255)")
    private String isbn;
    @Column(name = "page_count", columnDefinition = "int")
    private int pageCount;
    @Column(name = "author_id", columnDefinition = "bigint")
    private long authorId;

    public Book() {
        createdTime = new Date();
        updatedTime = createdTime;
    }

    @PreUpdate
    private void doPreUpdate() {
        updatedTime = new Date();
    }
}
