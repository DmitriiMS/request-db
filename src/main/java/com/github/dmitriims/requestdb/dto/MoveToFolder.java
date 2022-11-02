package com.github.dmitriims.requestdb.dto;

import lombok.Data;

@Data
public class MoveToFolder {
    private String requestId;
    private String folderId;
}
