package com.onlineshop.backend.repository;

import com.onlineshop.backend.model.SaleHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleHistoryRepository extends JpaRepository<SaleHistory, Long> {
}