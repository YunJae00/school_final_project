package com.school.project_spring_boot.repository.django;

import com.school.project_spring_boot.entity.django.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    List<TestResult> findByStockId(Long stockId);

    // stockId를 기준으로 최신 결과를 가져오는 메서드
    TestResult findTopByStockIdOrderByIdDesc(Long stockId);
}
