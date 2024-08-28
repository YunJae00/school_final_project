package com.school.project_spring_boot.dto.response.stock;

import com.school.project_spring_boot.dto.response.ResponseDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class FetchStockDataResponseDto extends ResponseDto {

    private FetchStockDataResponseDto(){
        super();
    }

    public static ResponseEntity<? super FetchStockDataResponseDto> success(){
        FetchStockDataResponseDto responseBody = new FetchStockDataResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
