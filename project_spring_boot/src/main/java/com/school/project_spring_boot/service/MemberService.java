package com.school.project_spring_boot.service;

import com.school.project_spring_boot.dto.StockWithRecentDataDto;
import com.school.project_spring_boot.entity.DailyStockData;
import com.school.project_spring_boot.entity.Member;
import com.school.project_spring_boot.entity.Stock;
import com.school.project_spring_boot.entity.MemberFavoriteStock;
import com.school.project_spring_boot.repository.DailyStockDataRepository;
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
    private final DailyStockDataRepository dailyStockDataRepository;

    public MemberService(MemberRepository memberRepository, StockRepository stockRepository, MemberFavoriteStockRepository memberFavoriteStockRepository, DailyStockDataRepository dailyStockDataRepository) {
        this.memberRepository = memberRepository;
        this.stockRepository = stockRepository;
        this.memberFavoriteStockRepository = memberFavoriteStockRepository;
        this.dailyStockDataRepository = dailyStockDataRepository;
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

    public List<StockWithRecentDataDto> getFavoriteStocksWithRecentData(String memberEmail) {
        Optional<Member> memberOpt = memberRepository.findByEmail(memberEmail);
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            return memberFavoriteStockRepository.findAllByMember(member).stream()
                    .map(favorite -> {
                        Stock stock = favorite.getStock();
                        List<DailyStockData> recentDataList = dailyStockDataRepository.findTopByStockOrderByBasDtDesc(stock);
                        DailyStockData recentData = recentDataList.isEmpty() ? null : recentDataList.get(0);
                        return recentData != null ? new StockWithRecentDataDto(
                                stock.getIsinCd(),
                                stock.getSrtnCd(),
                                stock.getItmsNm(),
                                stock.getMrktCls(),
                                recentData.getBasDt(),
                                recentData.getClpr(),
                                recentData.getHipr(),
                                recentData.getLopr(),
                                recentData.getMkp(),
                                recentData.getVs(),
                                recentData.getFltRt(),
                                recentData.getTrqu(),
                                recentData.getTrPrc(),
                                recentData.getLstgStCnt(),
                                recentData.getMrktTotAmt()
                        ) : new StockWithRecentDataDto(stock.getIsinCd(), stock.getSrtnCd(), stock.getItmsNm(), stock.getMrktCls(), null, null, null, null, null, null, null, null, null, null, null);
                    })
                    .collect(Collectors.toList());
        }
        return null;
    }
}
