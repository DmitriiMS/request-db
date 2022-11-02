package com.github.dmitriims.requestdb.repositories.elasticsearch;

import com.github.dmitriims.requestdb.model.ElasticRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticRequestRepository extends ElasticsearchRepository<ElasticRequest, String> {
}
