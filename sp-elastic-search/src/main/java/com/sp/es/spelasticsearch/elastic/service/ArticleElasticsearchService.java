package com.sp.es.spelasticsearch.elastic.service;

import java.io.IOException;

import org.elasticsearch.action.search.SearchResponse;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sp.es.spelasticsearch.elastic.model.ArticlesElasticResponse;
import com.sp.es.spelasticsearch.model.FilterContract;

public interface ArticleElasticsearchService {
	
	ArticlesElasticResponse findByStatus(FilterContract filterContract, Pageable page) throws JsonParseException, JsonMappingException, IOException;
	
	SearchResponse searchByOutlet(String outlet);

}
