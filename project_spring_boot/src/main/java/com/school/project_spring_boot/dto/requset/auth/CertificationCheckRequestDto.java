package com.school.project_spring_boot.dto.requset.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CertificationCheckRequestDto {

    @NotBlank
    private String email;

    @NotBlank
    private String certificationNumber;
}
