package com.franchise.Franchise.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RequestOrderDetail {

    @NotNull(message = "상품 id는 필수 입력입니다.")
    private Long productPriceId;

    @NotNull(message = "구매 개수는 필수 입력입니다.")
    private int orderAmount;

    private int discountPrice;
}
