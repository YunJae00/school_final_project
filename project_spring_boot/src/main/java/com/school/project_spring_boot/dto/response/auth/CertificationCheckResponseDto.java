package com.school.project_spring_boot.dto.response.auth;

import com.school.project_spring_boot.common.ResponseCode;
import com.school.project_spring_boot.common.ResponseMessage;
import com.school.project_spring_boot.dto.response.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class CertificationCheckResponseDto extends ResponseDto {

    private CertificationCheckResponseDto() {
        super();
    }

    public static ResponseEntity<CertificationCheckResponseDto> success(){
        CertificationCheckResponseDto responseBody = new CertificationCheckResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> certificationFail(){
        ResponseDto responseDto = new ResponseDto(ResponseCode.CERTIFICATION_FAIL, ResponseMessage.CERTIFICATION_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
    }
}
