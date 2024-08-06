package com.school.project_spring_boot.repository;

import com.school.project_spring_boot.entity.stock.IndexData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndexDataRepository extends JpaRepository<IndexData, Long> {
    Optional<IndexData> findByBasDtAndIdxNm(String basDt, String idxNm);
}
