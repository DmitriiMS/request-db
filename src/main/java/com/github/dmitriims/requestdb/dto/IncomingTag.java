package com.github.dmitriims.requestdb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "IncomingTag", description = "Входящая информация о теге")
public class IncomingTag {
    private String tagId;
    private String tagName;
}
