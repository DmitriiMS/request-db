package com.github.dmitriims.requestdb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "TagRequest", description = "Запрос на присваивание тега запросу")
public class TagRequest {
    private String requestId;
    private String tagId;
}
