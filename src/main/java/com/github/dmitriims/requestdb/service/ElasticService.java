package com.github.dmitriims.requestdb.service;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.github.dmitriims.requestdb.model.ElasticRequest;
import com.github.dmitriims.requestdb.model.Request;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ElasticService {

    private ElasticsearchOperations elasticsearchOperations;

    public ElasticService(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public List<String> performSearch(String query) {
        QueryBuilder qb = QueryBuilders.matchQuery("text", query).fuzziness(Fuzziness.AUTO);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withFilter(qb).build();
        SearchHits<ElasticRequest> rHits = elasticsearchOperations.search(searchQuery, ElasticRequest.class);
        List<String> mongoIds = new ArrayList<>();
        rHits.forEach(hit -> mongoIds.add(hit.getContent().getMongoId()));
        return mongoIds;
    }
}
