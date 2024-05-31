package com.franchise.Franchise.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSaleAndCnt {
    private Long storeId;
    private Long productId;
    private String name;
    private Long productDetailPriceId;
    private String productUnit;
    private int productAmount;
    private int totalSalesCount;
}
