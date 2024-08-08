package com.school.project_spring_boot.service.django;

import com.school.project_spring_boot.entity.django.TestResult;
import com.school.project_spring_boot.repository.django.TestResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestResultService {

    private final TestResultRepository testResultRepository;

    public TestResultService(TestResultRepository testResultRepository) {
        this.testResultRepository = testResultRepository;
    }

    public TestResult getLatestTestResultByStockId(Long stockId) {
        return testResultRepository.findTopByStockIdOrderByIdDesc(stockId);
    }
}
