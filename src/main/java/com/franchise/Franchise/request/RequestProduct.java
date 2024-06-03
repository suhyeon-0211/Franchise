package com.franchise.Franchise.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RequestProduct {
    
    // @NotBlank(message = "매장Id를 입력해주세요")
    private Long storeId;

    @NotBlank(message = "제품명을 입력해주세요")
    private String name;

    @NotNull(message = "가격을 입력해주세요")
    private Integer price;

    @NotBlank(message = "판매 단위를 입력해주세요")
    private String productUnit;

    @NotNull(message = "재고를 입력해주세요")
    private Integer productAmount;
}
