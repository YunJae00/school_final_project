package com.school.project_spring_boot.repository;

import com.school.project_spring_boot.entity.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByIsinCd(String isinCd);

    boolean existsByIsinCd(String isinCd);

    default Set<String> findAllIsinCodes() {
        return findAll().stream()
                .map(Stock::getIsinCd)
                .collect(Collectors.toSet());
    }
}
