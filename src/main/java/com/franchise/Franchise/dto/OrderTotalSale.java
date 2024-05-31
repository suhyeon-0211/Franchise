package com.franchise.Franchise.dto;

import lombok.Data;

@Data
public class OrderTotalSale {
    private Long storeId;
    private int totalSales;
    private int totalDiscounts;
    private int hallCount;
    private int deliveryCount;
    private int takeoutCount;
    private int creditcardCount;
    private int cashCount;
}
