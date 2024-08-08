package com.school.project_spring_boot.service.django;

import com.school.project_spring_boot.entity.django.PredictionResult;
import com.school.project_spring_boot.repository.django.PredictionResultRepository;
import org.springframework.stereotype.Service;

@Service
public class PredictionResultService {

    private final PredictionResultRepository predictionResultRepository;

    public PredictionResultService(PredictionResultRepository predictionResultRepository) {
        this.predictionResultRepository = predictionResultRepository;
    }

    public PredictionResult getLatestPredictionResultByStockId(Long stockId) {
        return predictionResultRepository.findTopByStockIdOrderByIdDesc(stockId);
    }
}
