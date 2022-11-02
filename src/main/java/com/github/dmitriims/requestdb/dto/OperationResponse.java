package com.github.dmitriims.requestdb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperationResponse {
    private boolean success;
    private String message;
}
