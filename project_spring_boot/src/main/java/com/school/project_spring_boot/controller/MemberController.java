package com.school.project_spring_boot.controller;

import com.school.project_spring_boot.dto.StockWithRecentDataDto;
import com.school.project_spring_boot.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/{memberEmail}/add")
    public void addFavoriteStock(@PathVariable String memberEmail, @RequestParam String isinCd) {
        memberService.addFavoriteStock(memberEmail, isinCd);
    }

    @DeleteMapping("/{memberEmail}/remove")
    public void removeFavoriteStock(@PathVariable String memberEmail, @RequestParam String isinCd) {
        memberService.removeFavoriteStock(memberEmail, isinCd);
    }

    @GetMapping("/{memberEmail}/recent")
    public List<StockWithRecentDataDto> getFavoriteStocksWithRecentData(@PathVariable String memberEmail) {
        return memberService.getFavoriteStocksWithRecentData(memberEmail);
    }
}
