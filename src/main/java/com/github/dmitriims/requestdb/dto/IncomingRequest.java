package com.github.dmitriims.requestdb.dto;

import lombok.Data;

@Data
public class IncomingRequest {
    private String requestId;
    private String requestText;
}
