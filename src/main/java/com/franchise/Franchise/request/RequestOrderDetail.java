package com.franchise.Franchise.request;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RequestOrderDetail {

    @NotNull(message = "상품 id는 필수 입력 값입니다.")
    private Long productPriceId;

    @NotNull(message = "구매 개수는 필수 입력 값입니다.")
    private Integer orderAmount;

    private int discountPrice;
}
