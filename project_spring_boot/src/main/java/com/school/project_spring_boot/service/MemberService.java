package com.school.project_spring_boot.service;

import com.school.project_spring_boot.entity.Member;
import com.school.project_spring_boot.entity.Stock;
import com.school.project_spring_boot.entity.MemberFavoriteStock;
import com.school.project_spring_boot.repository.MemberRepository;
import com.school.project_spring_boot.repository.StockRepository;
import com.school.project_spring_boot.repository.MemberFavoriteStockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final StockRepository stockRepository;
    private final MemberFavoriteStockRepository memberFavoriteStockRepository;

    public MemberService(MemberRepository memberRepository, StockRepository stockRepository, MemberFavoriteStockRepository memberFavoriteStockRepository) {
        this.memberRepository = memberRepository;
        this.stockRepository = stockRepository;
        this.memberFavoriteStockRepository = memberFavoriteStockRepository;
    }

    public void addFavoriteStock(String memberEmail, String isinCd) {
        Optional<Member> memberOpt = memberRepository.findByEmail(memberEmail);
        Optional<Stock> stockOpt = stockRepository.findByIsinCd(isinCd);

        if (memberOpt.isPresent() && stockOpt.isPresent()) {
            Member member = memberOpt.get();
            Stock stock = stockOpt.get();

            if (!memberFavoriteStockRepository.findByMemberAndStock(member, stock).isPresent()) {
                MemberFavoriteStock favoriteStock = new MemberFavoriteStock();
                favoriteStock.setMember(member);
                favoriteStock.setStock(stock);
                memberFavoriteStockRepository.save(favoriteStock);
            }
        }
    }

    public void removeFavoriteStock(String memberEmail, String isinCd) {
        Optional<Member> memberOpt = memberRepository.findByEmail(memberEmail);
        Optional<Stock> stockOpt = stockRepository.findByIsinCd(isinCd);

        if (memberOpt.isPresent() && stockOpt.isPresent()) {
            Member member = memberOpt.get();
            Stock stock = stockOpt.get();
            memberFavoriteStockRepository.deleteByMemberAndStock(member, stock);
        }
    }

    public List<Stock> getFavoriteStocks(String memberEmail) {
        Optional<Member> memberOpt = memberRepository.findByEmail(memberEmail);
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            return memberFavoriteStockRepository.findAllByMember(member).stream()
                    .map(MemberFavoriteStock::getStock)
                    .collect(Collectors.toList());
        }
        return null;
    }
}
