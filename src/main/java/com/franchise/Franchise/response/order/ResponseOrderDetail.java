package com.franchise.Franchise.response.order;

import java.util.*;

import com.franchise.Franchise.entity.OrderDetail;
import com.franchise.Franchise.enums.Status;

import io.swagger.models.Response;
import lombok.Data;

@Data
public class ResponseOrderDetail {
    private Long id;
    private Long productPriceId;
    private int orderAmount;
    private int price;
    private int discountPrice;
    private Status state;
    private Date createdAt;
    private Date updatedAt;

    public ResponseOrderDetail(OrderDetail orderDetail) {
        this.id = orderDetail.getId();
        this.productPriceId = orderDetail.getProductPriceId();
        this.orderAmount = orderDetail.getOrderAmount();
        this.price = orderDetail.getPrice();
        this.discountPrice = orderDetail.getDiscountPrice();
        this.state = orderDetail.getState();
        this.createdAt = orderDetail.getCreatedAt();
        this.updatedAt = orderDetail.getUpdatedAt();
    }
}
