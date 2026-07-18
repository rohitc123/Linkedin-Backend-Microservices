package com.example.linkedInApp.posts_service.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@Data
public class ApiError {
    private LocalDateTime timestamp;
    private String errors;
    private HttpStatus httpStatus;

    public ApiError() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(String errors,HttpStatus httpStatus) {
        this();
        this.httpStatus = httpStatus;
        this.errors=errors;
    }
}
