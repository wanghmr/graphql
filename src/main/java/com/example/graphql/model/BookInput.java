package com.example.graphql.model;

import lombok.Data;

/**
 * @author pcs
 * Date         2020/4/9
 * Description:
 */
@Data
public class BookInput {
    private String title;

    private String isbn;

    private int pageCount;

    private long authorId;
}
