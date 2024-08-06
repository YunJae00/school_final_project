package com.school.project_spring_boot.repository.stock;

import com.school.project_spring_boot.entity.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.List;
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

    // 주식 이름에 특정 문자열이 포함된 주식을 검색하는 메서드
    List<Stock> findByItmsNmContaining(String itmsNm);

}
