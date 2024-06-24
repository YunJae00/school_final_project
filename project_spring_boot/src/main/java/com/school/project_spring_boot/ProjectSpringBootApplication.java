package com.school.project_spring_boot;

import com.school.project_spring_boot.service.PublicDataPortalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectSpringBootApplication implements CommandLineRunner {

    private PublicDataPortalService publicDataPortalService;

    public ProjectSpringBootApplication(PublicDataPortalService publicDataPortalService) {
        this.publicDataPortalService = publicDataPortalService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProjectSpringBootApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // 시작 날짜와 종료 날짜를 지정합니다.
        String startDate = "20230101";
        String endDate = "20230131";
        publicDataPortalService.fetchAndSaveStockData(startDate, endDate);
    }
}
