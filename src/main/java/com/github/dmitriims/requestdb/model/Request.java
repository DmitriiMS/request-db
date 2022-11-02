package com.github.dmitriims.requestdb.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Document("requests")
@TypeAlias("request")
@Schema(name = "Request", description = "Запрос")
public class Request {

    @Id
    private String id;

    @Indexed
    private String text;

    private Long modifiedDate;
    private Long length;
    private Set<Tag> tags = new HashSet<>(10);
    private Folder folder;

    public Request(String text) {
        setTextAndLength(text);
        setModifiedDateToNow();
    }

    public void setTextAndLength(String text) {
        this.text = text;
        this.length = (long) this.text.length();
    }

    public void setModifiedDateToNow() {
        this.modifiedDate = System.currentTimeMillis();
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", modifiedDate=" + modifiedDate +
                ", length=" + length +
                ", tags=" + tags +
                '}';
    }
}
