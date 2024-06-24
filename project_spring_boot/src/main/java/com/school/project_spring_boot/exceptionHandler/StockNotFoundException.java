package com.school.project_spring_boot.exceptionHandler;

public class StockNotFoundException extends RuntimeException {
    public StockNotFoundException(String message) {
        super(message);
    }
}
