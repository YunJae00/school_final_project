package com.school.project_spring_boot.repository.django;

import com.school.project_spring_boot.entity.django.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {
}
