package com.franchise.Franchise.response.order;

import com.franchise.Franchise.entity.Order;
import com.franchise.Franchise.entity.OrderDetail;
import com.franchise.Franchise.enums.EatWhere;
import com.franchise.Franchise.enums.Payment;
import com.franchise.Franchise.enums.Status;

import lombok.Data;

@Data
public class ResponseOrderStatus {
    private Long id;
    private Long storeId;
    private int totalPrice;
    private int totalDiscount;
    private EatWhere eatWhere;
    private Payment payment;
    private Status state;
    private ResponseOrderDetail responseOrderDetail;

    public ResponseOrderStatus(Order order, ResponseOrderDetail responseOrderDetail) {
        this.id = order.getId();
        this.storeId = order.getStoreId();
        this.totalPrice = order.getTotalPrice();
        this.totalDiscount = order.getTotalDiscount();
        this.eatWhere = order.getEatWhere();
        this.payment = order.getPayment();
        this.state = order.getState();
        this.responseOrderDetail = responseOrderDetail;

    }
}
