package com.school.project_spring_boot.controller.stock;

import com.school.project_spring_boot.dto.response.stock.IndexDataDto;
import com.school.project_spring_boot.service.stock.IndexService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/index")
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
