package com.school.project_spring_boot.controller;

import com.school.project_spring_boot.dto.IndexDataDto;
import com.school.project_spring_boot.service.IndexService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/index")
public class IndexController {

    private final IndexService indexService;

    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    @PostMapping("/save-latest")
    public void saveLatestIndexData() {
        List<String> indexNames = Arrays.asList("코스피", "코스닥", "코스피 200", "코스닥 150", "KRX 100", "코스피 50");
        for (String indexName : indexNames) {
            indexService.fetchAndSaveLatestIndexData(indexName);
        }
    }

    @GetMapping("/latest-multiple")
    public List<IndexDataDto> getLatestMultipleIndexData() {
        return indexService.getAllSavedIndexData();
    }
}
