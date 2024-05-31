package com.franchise.Franchise.response.order;

import java.util.List;

import com.franchise.Franchise.entity.Order;
import com.franchise.Franchise.entity.OrderDetail;
import com.franchise.Franchise.enums.EatWhere;
import com.franchise.Franchise.enums.Payment;
import com.franchise.Franchise.enums.Status;

import lombok.Data;

@Data
public class ResponseOrder {
    
    private Long id;
    private Long storeId;
    private int totalPrice;
    private int totalDiscount;
    private EatWhere eatWhere;
    private Payment payment;
    private Status state;
    private List<ResponseOrderDetail> responseOrderDetailList; 

    public ResponseOrder(Order order, List<ResponseOrderDetail> responseOrderDetailList) {
        this.id = order.getId();
        this.storeId = order.getStoreId();
        this.totalPrice = order.getTotalPrice();
        this.totalDiscount = order.getTotalDiscount();
        this.eatWhere = order.getEatWhere();
        this.payment = order.getPayment();
        this.state = order.getState();
        this.responseOrderDetailList = responseOrderDetailList;
    }
}
