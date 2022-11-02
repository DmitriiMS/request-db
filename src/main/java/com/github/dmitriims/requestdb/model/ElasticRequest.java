package com.github.dmitriims.requestdb.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "request_index")
public class ElasticRequest {
    @Id
    private String id;

    @Field(type = FieldType.Text, name = "text")
    private String text;

    @Field(type = FieldType.Text, name = "mongoid")
    private String mongoId;
}
