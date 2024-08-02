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
        String url = "http://127.0.0.1:8000/api/train/?stock=" + stockName + "&start_date=" + startDate + "&episodes=" + episodes + "&window_size=" + windowSize;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return CompletableFuture.completedFuture(response);
    }

    @Async
    public CompletableFuture<ResponseEntity<String>> startTesting(String stockName, String startDate, int testRuns, int windowSize) {
        String url = "http://127.0.0.1:8000/api/test/?stock=" + stockName + "&start_date=" + startDate + "&test_runs=" + testRuns + "&window_size=" + windowSize;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return CompletableFuture.completedFuture(response);
    }

    @Async
    public CompletableFuture<ResponseEntity<String>> predictAction(String stockName, int daysAgo, int windowSize) {
        String url = "http://127.0.0.1:8000/api/predict/?stock=" + stockName + "&days_ago=" + daysAgo + "&window_size=" + windowSize;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return CompletableFuture.completedFuture(response);
    }
}
