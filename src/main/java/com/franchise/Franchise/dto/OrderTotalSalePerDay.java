package com.franchise.Franchise.dto;

import java.util.Date;

import lombok.Data;

@Data
public class OrderTotalSalePerDay {
    private Long storeId;
    private int totalSales;
    private int totalDiscounts;
    private int hallCount;
    private int deliveryCount;
    private int takeoutCount;
    private int creditcardCount;
    private int cashCount;
    private Date orderDate;
}
