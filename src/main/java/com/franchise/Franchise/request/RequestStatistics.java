package com.franchise.Franchise.request;

import javax.validation.constraints.NotBlank;

import com.franchise.Franchise.enums.DatePick;
import com.franchise.Franchise.valid.ValidEnum;

import lombok.Data;

@Data
public class RequestStatistics {
    @NotBlank(message = "시작날짜는 반드시 입력해야 합니다.")
    private String startDate;
    private String endDate;
    @ValidEnum(enumClass = DatePick.class)
    private DatePick unit;

    public RequestStatistics(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
