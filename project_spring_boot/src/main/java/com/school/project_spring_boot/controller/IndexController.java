package com.school.project_spring_boot.controller;

import com.school.project_spring_boot.entity.IndexData;
import com.school.project_spring_boot.service.IndexService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/index")
public class IndexController {

    private final IndexService indexService;

    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    @GetMapping("/latest")
    public List<IndexData> getLatestIndexData(@RequestParam String indexName) {
        return indexService.fetchAndSaveLatestIndexData(indexName);
    }
}
