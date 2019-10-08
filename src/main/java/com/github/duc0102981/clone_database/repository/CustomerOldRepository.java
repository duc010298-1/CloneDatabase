package com.github.duc0102981.clone_database.repository;

import com.github.duc0102981.clone_database.entity.CustomerOldEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOldRepository extends JpaRepository<CustomerOldEntity, Integer> {
}

