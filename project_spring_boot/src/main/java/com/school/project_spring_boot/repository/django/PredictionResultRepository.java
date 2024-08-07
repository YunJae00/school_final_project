package com.school.project_spring_boot.repository.django;

import com.school.project_spring_boot.entity.django.PredictionResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionResultRepository extends JpaRepository<PredictionResult, Long> {
}
