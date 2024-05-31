package com.franchise.Franchise.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.franchise.Franchise.entity.Product;

@Mapper
public interface ProductMapper {
    List<Product> selectProductList();
}
