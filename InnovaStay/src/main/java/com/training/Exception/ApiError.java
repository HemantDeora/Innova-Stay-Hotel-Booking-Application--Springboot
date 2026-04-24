package com.training.Exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ApiError {
    private String message;
    private int status;
    private LocalDateTime timestamp;
    private String path;
}
