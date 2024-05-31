package com.franchise.Franchise.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSalePerDay {
    private Long storeId;
    private Long productId;
    private String name;
    private Long productDetailPriceId;
    private String productUnit;
    private String productAmount;
    private int totalSalesCount;
    private Date orderDate;
}
