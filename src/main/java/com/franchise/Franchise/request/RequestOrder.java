package com.franchise.Franchise.request;

import java.util.*;

import javax.validation.constraints.NotNull;

import com.franchise.Franchise.enums.EatWhere;
import com.franchise.Franchise.enums.Payment;
import com.franchise.Franchise.valid.ValidEnum;

import lombok.Data;

@Data
public class RequestOrder {
    
    @NotNull(message = "매장 id는 필수 입력입니다.")
    private Long storeId;

    @ValidEnum(enumClass = EatWhere.class)
    private EatWhere eatWhere;

    @ValidEnum(enumClass = Payment.class)
    private Payment payment;
    
    private List<RequestOrderDetail> requestOrderDetailList;
}
