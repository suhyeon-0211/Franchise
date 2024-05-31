package com.franchise.Franchise.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.franchise.Franchise.dto.ProductSaleAndCnt;
import com.franchise.Franchise.dto.ProductSalePerDay;

@Mapper
public interface ProductDetailPriceMapper {
    List<ProductSaleAndCnt> selectProductDetailSaleAndCnt(
                                @Param("startDate") String startDate,
                                @Param("endDate") String endDate
    );
    List<ProductSaleAndCnt> selectProductDetailSaleAndCntByStoreId(
                                @Param("startDate") String startDate,
                                @Param("endDate") String endDate,
                                @Param("storeId") Long storeId
    );
    List<ProductSalePerDay> selectProductDetailSalePerDay(
                                @Param("startDate") String startDate,
                                @Param("endDate") String endDate
    );
    List<ProductSalePerDay> selectProductDetailSalePerDayByStoreId(
                                @Param("startDate") String startDate,
                                @Param("endDate") String endDate,
                                @Param("storeId") Long storeId
    );
    

}
