package com.franchise.Franchise.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.franchise.Franchise.dto.OrderTotalSale;
import com.franchise.Franchise.dto.OrderTotalSalePerDay;
import com.franchise.Franchise.dto.ProductSaleAndCnt;

@Mapper
public interface OrderMapper {
    List<OrderTotalSale> selectOrderTotalSaleList(
                                @Param("startDate") String startDate,
                                @Param("endDate") String endDate
    );
    List<OrderTotalSale> selectOrderTotalSaleListByStoreId(
                                @Param("startDate") String startDate,
                                @Param("endDate") String endDate,
                                @Param("storeId") Long storeId
    );
    List<OrderTotalSalePerDay> selectOrderTotalSaleListPerDay(
                                @Param("startDate") String startDate,
                                @Param("endDate") String endDate
    );
    List<OrderTotalSalePerDay> selectOrderTotalSaleListPerDayByStoreId(
                                @Param("startDate") String startDate,
                                @Param("endDate") String endDate,
                                @Param("storeId") Long storeId
    );
}
