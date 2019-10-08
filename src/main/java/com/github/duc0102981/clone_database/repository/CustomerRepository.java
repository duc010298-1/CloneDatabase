package com.github.duc0102981.clone_database.repository;

import com.github.duc0102981.clone_database.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {
}
