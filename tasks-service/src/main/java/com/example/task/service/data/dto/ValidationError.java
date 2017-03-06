package com.example.task.service.data.dto;

import lombok.Data;

@Data
public class ValidationError {
    private String code;
    private String message;
}
