package com.example.graphql.model;

import lombok.Data;

/**
 * @author pcs
 * Date         2020/4/20
 * Description:
 */
@Data
public class StockPriceUpdate {

    private String dateTime;

    private String stockCode;

    private Float stockPrice;

    private Float stockPriceChange;
}
