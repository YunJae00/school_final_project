package com.school.project_spring_boot.service.django;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class DjangoService {

    private final RestTemplate restTemplate;

    public DjangoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public CompletableFuture<ResponseEntity<String>> startTraining(String stockName, String startDate, int episodes, int windowSize) {
        String url = String.format("http://127.0.0.1:8000/api/train/?stock=%s&start_date=%s&episodes=%d&window_size=%d",
                stockName, startDate, episodes, windowSize);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return CompletableFuture.completedFuture(response);
    }

    @Async
    public CompletableFuture<ResponseEntity<String>> startTesting(String stockName, String startDate, int testRuns, int windowSize) {
        String url = String.format("http://127.0.0.1:8000/api/test/?stock=%s&start_date=%s&test_runs=%d&window_size=%d",
                stockName, startDate, testRuns, windowSize);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return CompletableFuture.completedFuture(response);
    }

    @Async
    public CompletableFuture<ResponseEntity<String>> predictAction(String stockName, int daysAgo, int windowSize) {
        String url = String.format("http://127.0.0.1:8000/api/predict/?stock=%s&days_ago=%d&window_size=%d",
                stockName, daysAgo, windowSize);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return CompletableFuture.completedFuture(response);
    }
}
