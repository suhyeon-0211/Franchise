package com.franchise.Franchise.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.franchise.Franchise.enums.EatWhere;
import com.franchise.Franchise.enums.Payment;
import com.franchise.Franchise.valid.ValidEnum;
import com.franchise.Franchise.valid.ValidationGroups;

import lombok.Data;

@Data
public class RequestOrder {
    
    @NotNull(message = "매장 id는 필수 입력 값입니다.")
    private Long storeId;

    @NotBlank(message = "홀, 배달, 포장 선택은 필수 입력 값입니다.", groups = ValidationGroups.NotBlankGroup.class)
    @ValidEnum(enumClass = EatWhere.class, message = "허용되지 않은 값입니다. 다시 입력해주세요", groups = ValidationGroups.EnumGroup.class)
    private String eatWhere;

    @NotBlank(message = "결제수단은 필수 입력 값입니다.", groups = ValidationGroups.NotBlankGroup.class)
    @ValidEnum(enumClass = Payment.class, message = "허용되지 않은 값입니다. 다시 입력해주세요", groups = ValidationGroups.EnumGroup.class)
    private String payment;
    
    @NotNull(message = "상세 결제가 1개 이상 등록되어야 합니다.")
    @Size(min = 1, message = "상세 결제가 1개 이상 등록되어야 합니다.")
    @Valid
    private List<RequestOrderDetail> requestOrderDetailList;
}
