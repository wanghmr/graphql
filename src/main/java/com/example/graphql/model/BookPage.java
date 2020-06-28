package com.example.graphql.model;

import lombok.Data;

import java.util.List;

/**
 * @author pcs
 * Date         2020/4/14
 * Description: 书籍的分页实体
 */
@Data
public class BookPage {

    private List<Book> content;

    private PageBase pageBase;
}
