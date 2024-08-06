package com.school.project_spring_boot.controller.django;

import com.school.project_spring_boot.service.django.DjangoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/django")
public class DjangoController {

    private final DjangoService djangoService;

    public DjangoController(DjangoService djangoService) {
        this.djangoService = djangoService;
    }

    @GetMapping("/train")
    public CompletableFuture<ResponseEntity<String>> trainModel(
            @RequestParam String stockName,
            @RequestParam String startDate,
            @RequestParam int episodes,
            @RequestParam int windowSize) {
        return djangoService.startTraining(stockName, startDate, episodes, windowSize);
    }

    @GetMapping("/test")
    public CompletableFuture<ResponseEntity<String>> testModel(
            @RequestParam String stockName,
            @RequestParam String startDate,
            @RequestParam int testRuns,
            @RequestParam int windowSize) {
        return djangoService.startTesting(stockName, startDate, testRuns, windowSize);
    }

    @GetMapping("/predict")
    public CompletableFuture<ResponseEntity<String>> predictAction(
            @RequestParam String stockName,
            @RequestParam int daysAgo,
            @RequestParam int windowSize) {
        return djangoService.predictAction(stockName, daysAgo, windowSize);
    }
}
