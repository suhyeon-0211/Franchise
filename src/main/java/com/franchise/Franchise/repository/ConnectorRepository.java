package com.franchise.Franchise.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franchise.Franchise.entity.Connector;
import java.util.List;


public interface ConnectorRepository extends JpaRepository<Connector, Long> {
    boolean existsByEmail(String email);
    Optional<Connector> findByEmailAndPassword(String email, String password);
    Optional<Connector> findByAccessToken(String accessToken);
}
