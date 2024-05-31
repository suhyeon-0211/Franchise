package com.franchise.Franchise.response.order;

import java.util.List;

import com.franchise.Franchise.dto.OrderTotalSale;
import com.franchise.Franchise.dto.OrderTotalSalePerDay;

import lombok.Data;

@Data
public class ResponseOrderCompleted {
    private List<OrderTotalSale> orderTotalSaleList;
    private List<OrderTotalSalePerDay> orderTotalSalePerDayList;

    public ResponseOrderCompleted(List<OrderTotalSale> orderTotalSaleList, List<OrderTotalSalePerDay> orderTotalSalePerDayList) {
        this.orderTotalSaleList = orderTotalSaleList;
        this.orderTotalSalePerDayList = orderTotalSalePerDayList;
    }
}
