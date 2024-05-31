package com.franchise.Franchise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.franchise.Franchise.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
}
