package com.github.dmitriims.requestdb.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document("tags")
@TypeAlias("tag")
public class Tag {

    @Id
    private String id;

    @Indexed
    private String tagName;

    public Tag (String tagName) {
        this.tagName = tagName;
    }
}
