package com.franchise.Franchise.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductSale {
    private List<ProductSaleAndCnt> productSaleAndCntList;
    private List<ProductSalePerDay> productSalePerDayList;

    public ProductSale (List<ProductSaleAndCnt> productSaleAndCntList, List<ProductSalePerDay> productSalePerDayList) {
        this.productSaleAndCntList = productSaleAndCntList;
        this.productSalePerDayList = productSalePerDayList;
    }
}
