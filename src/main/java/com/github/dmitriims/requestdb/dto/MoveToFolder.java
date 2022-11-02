package com.github.dmitriims.requestdb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "MoveToFolder", description = "Запрос на перемещение запроса в папку")
public class MoveToFolder {
    private String requestId;
    private String folderId;
}
