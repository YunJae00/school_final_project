package com.school.project_spring_boot.entity.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Certification {
    @Id
    @Column(name = "member_email")
    private String memberEmail;

    @Column(name = "certification_number;")
    private String certificationNumber;

    public Certification(String memberEmail, String certificationNumber) {
        this.memberEmail = memberEmail;
        this.certificationNumber = certificationNumber;
    }
}
