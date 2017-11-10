package com.sp.es.spelasticsearch.elastic.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sp.es.spelasticsearch.elastic.model.ArticleElastic;
import com.sp.es.spelasticsearch.elastic.model.ArticlesElasticResponse;
import com.sp.es.spelasticsearch.model.FilterContract;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;

@Service
public class ArticleElasticsearchServiceImpl implements ArticleElasticsearchService{
	
	
	@Autowired
	Client elasticSearchClient;

	@Autowired
	ObjectMapper objectMapper;
	
	@Value("${elasticsearch.indexname}")
	private String EsIndexName;

	@Value("${elasticsearch.type}")
	private String EsType;


	@Override
	public ArticlesElasticResponse findByStatus(FilterContract filterContract, Pageable page) throws JsonParseException, JsonMappingException, IOException {
		QueryBuilder query = boolQuery()
				.must(termQuery("publicationNameLowercase.keyword", "twitter"));

		SearchResponse searchResponse = elasticSearchClient.prepareSearch(EsIndexName).setTypes(EsType).setQuery(query)
				.execute().actionGet();

		SearchHit[] hits = searchResponse.getHits().getHits();
		ArticlesElasticResponse response = new ArticlesElasticResponse();
		List<ArticleElastic> articles = new ArrayList<ArticleElastic>();
		for (SearchHit hit : hits) {
			ArticleElastic article = objectMapper.readValue(hit.getSourceAsString(), ArticleElastic.class);
			articles.add(article);
		}
		response.setArticles(articles);
		return response;

	}


	@Override
	public SearchResponse searchByOutlet(String outlet) {
		QueryBuilder query = boolQuery()
				.should(queryStringQuery("Twitter")
						.lenient(true)
						.field("publicationName")// outlet
		);

		SearchResponse searchResponse = elasticSearchClient.prepareSearch(EsIndexName).setTypes(EsType).setQuery(query).execute()
				.actionGet();
		
		
		return searchResponse;
	}
	
	

}
