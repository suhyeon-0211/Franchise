package com.franchise.Franchise.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franchise.Franchise.entity.ProductDetailPrice;

public interface ProductDetailPriceRepository extends JpaRepository<ProductDetailPrice, Long>{
    Optional<ProductDetailPrice> findByProductIdAndProductUnit(Long productId, String productUnit);
}
