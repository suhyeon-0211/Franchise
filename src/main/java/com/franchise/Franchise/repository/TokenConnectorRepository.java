package com.franchise.Franchise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franchise.Franchise.entity.TokenConnector;

public interface TokenConnectorRepository extends JpaRepository<TokenConnector, Long> {
    
}
