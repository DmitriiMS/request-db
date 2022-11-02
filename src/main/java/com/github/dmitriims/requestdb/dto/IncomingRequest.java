package com.github.dmitriims.requestdb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "IncomingRequest", description = "Входящая информация о запросе")
public class IncomingRequest {
    private String requestId;
    private String requestText;
}
