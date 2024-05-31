package com.franchise.Franchise.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.franchise.Franchise.domain.UserAccessToken;
import com.franchise.Franchise.dto.ProductSale;
import com.franchise.Franchise.dto.ProductSaleAndCnt;
import com.franchise.Franchise.dto.ProductSalePerDay;
import com.franchise.Franchise.enums.DatePick;
import com.franchise.Franchise.exceptions.BusinessLogicException;
import com.franchise.Franchise.exceptions.BusinessLogicExceptionDefinedReason;
import com.franchise.Franchise.request.RequestStatistics;

public class SetDate {
    public static RequestStatistics setDate(RequestStatistics request, UserAccessToken token) {
        if (request.getStartDate().isEmpty()) {
            throw new BusinessLogicException(BusinessLogicExceptionDefinedReason.NOT_FOUND_DATE);
        } else {
            int dayAmount = 0;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = formatter.parse(request.getStartDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            if (request.getUnit().equals(DatePick.DAY)) {
                // 시작날짜만 조회
                dayAmount = 0;
            } else if (request.getUnit().equals(DatePick.WEEK)) {
                // 시작날짜 기준 7일 조회
                dayAmount = 7;
            } else if (request.getUnit().equals(DatePick.MONTH)) {
                // 시작날짜 기준 1달 조회
                dayAmount = 30;
            } else if (request.getUnit().equals(DatePick.YEAR)) {
                // 시작날짜 기준 1년 조회
                dayAmount = 365;
            } else if (!request.getEndDate().isEmpty()) {
                // 시작날짜부터 마지막날까지 조회
                return new RequestStatistics(request.getStartDate(), request.getEndDate());
            } else if (request.getEndDate().isEmpty()) {
                // 시작날짜부터 오늘까지 조회
                return new RequestStatistics(request.getStartDate(), formatter.format(LocalDate.now()));
            } else {
                throw new BusinessLogicException(BusinessLogicExceptionDefinedReason.NOT_FOUND_DATE);
            }
            cal.add(Calendar.DATE, -dayAmount);
            String endDate = formatter.format(cal.getTime());
            return new RequestStatistics(request.getStartDate(), endDate);
        }
    }
}
