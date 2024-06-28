package com.school.project_spring_boot.repository;

import com.school.project_spring_boot.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByIsinCd(String isinCd);
}
