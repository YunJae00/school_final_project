package com.school.project_spring_boot.repository;

import com.school.project_spring_boot.entity.auth.Member;
import com.school.project_spring_boot.entity.MemberFavoriteStock;
import com.school.project_spring_boot.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberFavoriteStockRepository extends JpaRepository<MemberFavoriteStock, Long> {
    Optional<MemberFavoriteStock> findByMemberAndStock(Member member, Stock stock);
    List<MemberFavoriteStock> findAllByMember(Member member);
    void deleteByMemberAndStock(Member member, Stock stock);
}
