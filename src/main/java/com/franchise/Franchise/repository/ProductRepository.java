package com.franchise.Franchise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franchise.Franchise.entity.Product;
import com.franchise.Franchise.enums.Status;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByStoreIdAndName(Long storeId, String name);
    List<Product> findByStoreIdAndStateIsNot(Long storeId, Status state);
    List<Product> findAllByStateIsNot(Status state);
}
