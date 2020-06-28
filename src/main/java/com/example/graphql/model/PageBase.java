package com.example.graphql.model;

import lombok.Data;

/**
 * @author pcs
 * Date         2020/4/14
 * Description:
 */
@Data
public class PageBase {
    /**
     * 当前页数，从0开始
     */
    private int pageNumber;
    /**
     * 一页多少条，默认20条
     */
    private int pageSize;

    /**
     * 是否有下一页
     */
    private boolean hasNextPage;

    /**
     * 总页数
     */
    private int totalPages;

    /**
     * 总条数
     */
    private long totalElements;
}
