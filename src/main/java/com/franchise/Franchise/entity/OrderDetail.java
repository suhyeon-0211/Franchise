package com.franchise.Franchise.entity;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.ibatis.annotations.Update;
import org.hibernate.annotations.UpdateTimestamp;

import com.franchise.Franchise.enums.Status;
import com.franchise.Franchise.request.RequestOrderDetail;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@ApiModel("결제 상세 테이블")
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //private Long orderId;
    @Schema(description = "상품 상세 id", example = "")
    private Long productPriceId;
    @Schema(description = "구매 개수", example = "")
    private int orderAmount;
    @Schema(description = "가격", example = "")
    private int price;
    @Schema(description = "할인 금액", example = "")
    private int discountPrice;

    @Schema(description = "상태", example = "ACTIVATE, DEACTIVATE...")
    @Enumerated(EnumType.STRING)
    private Status state;

    @Schema(description = "생성 날짜", example = "")
    @UpdateTimestamp
    @Column(name = "createdAt", updatable = false)
    private Date createdAt;

    @Schema(description = "수정 날짜", example = "")
    @UpdateTimestamp
    private Date updatedAt;

    @Schema(description = "결제 테이블 매핑", example = "")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="orderId", unique = true)
    private Order order;

    public OrderDetail(RequestOrderDetail detail, int price) {
        this.productPriceId = detail.getProductPriceId();
        this.orderAmount = detail.getOrderAmount();
        this.price = price;
        this.discountPrice = detail.getDiscountPrice();
        this.state = Status.ACTIVATE;
    }
}
