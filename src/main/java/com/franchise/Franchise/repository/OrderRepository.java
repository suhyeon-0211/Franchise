package com.franchise.Franchise.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.franchise.Franchise.entity.Order;
import com.franchise.Franchise.enums.Status;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByStateIsNot(Status state);
    List<Order> findByStoreIdAndStateIsNot(Long storeId, Status state);
}
