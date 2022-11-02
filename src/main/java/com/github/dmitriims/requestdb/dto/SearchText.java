package com.github.dmitriims.requestdb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "SearchText", description = "Поисковый запрос для нечёткого поиска")
public class SearchText {
    private String searchText;
}
