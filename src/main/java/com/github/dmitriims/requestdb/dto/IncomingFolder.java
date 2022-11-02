package com.github.dmitriims.requestdb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "IncomingFolder", description = "Входящая информация о папке")
public class IncomingFolder {
    private String folderId;
    private String folderName;
}
