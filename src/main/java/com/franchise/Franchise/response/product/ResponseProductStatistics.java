package com.franchise.Franchise.response.product;

import java.util.Map;

import lombok.Data;

@Data
public class ResponseProductStatistics {
    
     private Long storeId;

     private Long productId;

     private Long productDetailPriceId;

     private int productSellAmount;

     private int productSellRank;

     private Map<Integer, Integer> productSellPerDay;
}
