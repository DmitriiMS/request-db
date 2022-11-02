package com.github.dmitriims.requestdb.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document("folders")
@TypeAlias("folder")
public class Folder {

    @Id
    private String id;

    @Indexed
    private String folderName;

    public Folder(String folderName) {
        this.folderName = folderName;
    }
}
