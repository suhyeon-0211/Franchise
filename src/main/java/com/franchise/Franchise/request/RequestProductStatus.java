package com.franchise.Franchise.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RequestProductStatus {
    
    @NotNull(message = "제품 상세 번호를 입력해주세요")
    private Long id;

    @NotBlank(message = "상태를 선택해주세요")
    private String status;
}
