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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import com.franchise.Franchise.enums.EatWhere;
import com.franchise.Franchise.enums.Payment;
import com.franchise.Franchise.enums.Status;
import com.franchise.Franchise.request.RequestOrder;

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
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("결제 테이블")
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "매장 번호", example = "")
    private Long storeId;
    @Schema(description = "총 금액", example = "")
    private int totalPrice;
    @Schema(description = "총 할인 금액", example = "")
    private int totalDiscount;

    @Schema(description = "홀, 배달, 포장", example = "HALL, DELIVERY, TAKEOUT")
    @Enumerated(EnumType.STRING)
    private EatWhere eatWhere;

    @Schema(description = "결제 수단", example = "CREDITCARD, CASH")
    @Enumerated(EnumType.STRING)
    private Payment payment;

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

    @Schema(description = "결제 상세 테이블 매핑", example = "")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    private List<OrderDetail> orderDetailList = new ArrayList<>();

    public Order(RequestOrder reqeustOrder, List<OrderDetail> orderDetailList, int totalPrice, int totalDiscount) {
        this.storeId = reqeustOrder.getStoreId();
        this.totalPrice = totalPrice;
        this.totalDiscount  = totalDiscount;
        this.eatWhere = reqeustOrder.getEatWhere();
        this.payment = reqeustOrder.getPayment();
        this.state = Status.ACTIVATE;
        this.orderDetailList = orderDetailList;
    }
}
