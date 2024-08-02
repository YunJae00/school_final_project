package com.school.project_spring_boot.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(10))  // 연결 타임아웃 설정
                .setReadTimeout(Duration.ofMinutes(6 * 60)) // 읽기 타임아웃 설정, 6시간으로 설정
                .build();
    }
}
