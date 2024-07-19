package com.school.project_spring_boot.repository.auth;

import com.school.project_spring_boot.entity.auth.Certification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificationRepository extends JpaRepository<Certification, String> {
    Optional<Certification> findByMemberEmail(String email);

    @Transactional
    void deleteByMemberEmail(String email);
}
