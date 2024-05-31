package com.franchise.Franchise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franchise.Franchise.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    
}
