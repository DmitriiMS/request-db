package com.github.dmitriims.requestdb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(name = "OperationResponse", description = "Ответ о состоянии операции")
public class OperationResponse {
    private boolean success;
    private String message;
}
