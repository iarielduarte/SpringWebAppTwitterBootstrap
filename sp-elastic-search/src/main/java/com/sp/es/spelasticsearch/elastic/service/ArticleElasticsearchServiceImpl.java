package com.sp.es.spelasticsearch.elastic.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentHelper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sp.es.spelasticsearch.elastic.response.ArticleElastic;
import com.sp.es.spelasticsearch.elastic.response.ArticleElasticAggregations;
import com.sp.es.spelasticsearch.elastic.response.ArticlesElasticResponse;
import com.sp.es.spelasticsearch.model.FilterContract;
import com.sp.es.spelasticsearch.model.StatusEnum;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

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
	public ArticlesElasticResponse filteredSearch(FilterContract filterContract, Pageable page)
			throws JsonParseException, JsonMappingException, IOException {
		BoolQueryBuilder nestedQueries = boolQuery().must(termQuery("humanFiltered.routingOption.keyword", "HumanMonitoring"));
		if(filterContract.getStatuses()!=null && !filterContract.getStatuses().isEmpty()){
			
			List<String> statusesString = new ArrayList<String>();
			for(StatusEnum status : filterContract.getStatuses()){
				statusesString.add(status.toString());
			}
			nestedQueries.must(termsQuery("humanFiltered.status.keyword", statusesString));
		}
		if(filterContract.getDataGroupIds()!=null && !filterContract.getDataGroupIds().isEmpty()){
			nestedQueries.must(termsQuery("humanFiltered.dataGroupId", filterContract.getDataGroupIds()));
		}
		if(filterContract.getCategories()!=null && !filterContract.getCategories().isEmpty()){
			nestedQueries.must(termsQuery("humanFiltered.category.keyword", filterContract.getCategories()));
		}

		BoolQueryBuilder finalQuery = boolQuery().must(nestedQuery("humanFiltered", nestedQueries, ScoreMode.None));
		if(filterContract.getOutlets()!=null && !filterContract.getOutlets().isEmpty()){
			finalQuery.must(termsQuery("publicationNameLowercase.keyword", filterContract.getOutlets()));
		}
		if(filterContract.getCountries()!=null && !filterContract.getCountries().isEmpty()){
			List<Integer> countries = filterContract.getCountries().stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
			finalQuery.must(termsQuery("bestCountry", countries));
		}
		if(filterContract.getMediaIds()!=null && !filterContract.getMediaIds().isEmpty()){
			finalQuery.must(termsQuery("cpreMediaType", filterContract.getMediaIds()));
		}

		SearchResponse searchResponse = elasticSearchClient.prepareSearch(EsIndexName).setTypes(EsType).setQuery(finalQuery)
				.setFrom(page.getPageNumber())
				.setSize(page.getPageSize())
			    .addAggregation(AggregationBuilders.terms("medias").field("cpreMediaType").size(100))
			    .addAggregation(AggregationBuilders.terms("regions").field("bestCountry").size(100))
			    .addAggregation(AggregationBuilders.terms("outlets").field("publicationNameLowercase.keyword").size(200))
			    //Nested Aggs
			    .addAggregation(AggregationBuilders.nested("humanFiltered", "humanFiltered")
			      .subAggregation(AggregationBuilders
			    		  .terms("statuses")
			    		  .subAggregation(AggregationBuilders.reverseNested("count"))
			    		  .field("humanFiltered.status.keyword"))
			      .subAggregation(AggregationBuilders
			    		  .terms("datagroups")
			    		  .subAggregation(AggregationBuilders.reverseNested("count"))
			    		  .field("humanFiltered.dataGroupId").size(100))
			      .subAggregation(AggregationBuilders
			    		  .terms("categories")
			    		  .subAggregation(AggregationBuilders.reverseNested("count"))
			    		  .field("humanFiltered.category.keyword").size(100)))
			    .execute().actionGet();

		SearchHit[] hits = searchResponse.getHits().getHits();
		ArticlesElasticResponse response = new ArticlesElasticResponse();
		List<ArticleElastic> articles = new ArrayList<ArticleElastic>();
		for (SearchHit hit : hits) {
			ArticleElastic article = objectMapper.readValue(hit.getSourceAsString(), ArticleElastic.class);
			articles.add(article);
		}
		response.setArticles(articles);
		response.setTook(searchResponse.getTookInMillis());
		response.setTotal(searchResponse.getHits().getTotalHits());
		List<Aggregation> aggregations = searchResponse.getAggregations().asList();
		ArticleElasticAggregations elasticAggs = new ArticleElasticAggregations();
		for(Aggregation aggregation : aggregations){
			if(aggregation.getName().equals("humanFiltered")){
				ArticleElasticAggregations aggs = objectMapper.readValue(XContentHelper.toString(aggregation), ArticleElasticAggregations.class);
				elasticAggs.setHumanFiltered(aggs.getHumanFiltered());
			}
			if(aggregation.getName().equals("medias")){
				ArticleElasticAggregations aggs = objectMapper.readValue(XContentHelper.toString(aggregation), ArticleElasticAggregations.class);
				elasticAggs.setMedias(aggs.getMedias());
			}
			if(aggregation.getName().equals("regions")){
				ArticleElasticAggregations aggs = objectMapper.readValue(XContentHelper.toString(aggregation), ArticleElasticAggregations.class);
				elasticAggs.setRegions(aggs.getRegions());
			}
			if(aggregation.getName().equals("outlets")){
				ArticleElasticAggregations aggs = objectMapper.readValue(XContentHelper.toString(aggregation), ArticleElasticAggregations.class);
				elasticAggs.setOutlets(aggs.getOutlets());
			}
		}
		response.setAggregations(elasticAggs);		
		return response;

	}
	
	/**
	 * T
	 * Esta es la primera version de la query
	 */
	public ArticlesElasticResponse filteredSearchFirstQuery(FilterContract filterContract, Pageable page)
			throws JsonParseException, JsonMappingException, IOException {
		BoolQueryBuilder nestedQueries = boolQuery().must(termQuery("humanFiltered.routingOption.keyword", "HumanMonitoring"));
		if(filterContract.getStatuses()!=null && !filterContract.getStatuses().isEmpty()){
			
			List<String> statusesString = new ArrayList<String>();
			for(StatusEnum status : filterContract.getStatuses()){
				statusesString.add(status.toString());
			}
			nestedQueries.must(termsQuery("humanFiltered.status.keyword", statusesString));
		}
		if(filterContract.getDataGroupIds()!=null && !filterContract.getDataGroupIds().isEmpty()){
			nestedQueries.must(termsQuery("humanFiltered.dataGroupId", filterContract.getDataGroupIds()));
		}
		if(filterContract.getCategories()!=null && !filterContract.getCategories().isEmpty()){
			nestedQueries.must(termsQuery("humanFiltered.category.keyword", filterContract.getCategories()));
		}

		BoolQueryBuilder finalQuery = boolQuery().must(nestedQuery("humanFiltered", nestedQueries, ScoreMode.None));
		if(filterContract.getOutlets()!=null && !filterContract.getOutlets().isEmpty()){
			finalQuery.must(termsQuery("publicationNameLowercase.keyword", filterContract.getOutlets()));
		}
		if(filterContract.getCountries()!=null && !filterContract.getCountries().isEmpty()){
			List<Integer> countries = filterContract.getCountries().stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
			finalQuery.must(termsQuery("bestCountry", countries));
		}
		if(filterContract.getMediaIds()!=null && !filterContract.getMediaIds().isEmpty()){
			finalQuery.must(termsQuery("cpreMediaType", filterContract.getMediaIds()));
		}

		SearchResponse searchResponse = elasticSearchClient.prepareSearch(EsIndexName).setTypes(EsType).setQuery(finalQuery)
				.setFrom(page.getPageNumber())
				.setSize(page.getPageSize())
			    .addAggregation(AggregationBuilders.terms("medias").field("cpreMediaType"))
			    .addAggregation(AggregationBuilders.terms("regions").field("bestCountry"))
			    .addAggregation(AggregationBuilders.terms("outlets").field("publicationNameLowercase.keyword"))
			    //Nested Aggs
			    .addAggregation(AggregationBuilders.nested("humanFiltered", "humanFiltered")
			      .subAggregation(AggregationBuilders.terms("statuses").field("humanFiltered.status.keyword"))
			      .subAggregation(AggregationBuilders.terms("datagroups").field("humanFiltered.dataGroupId.keyword"))
			      .subAggregation(AggregationBuilders.terms("categories").field("humanFiltered.category.keyword")))
			    .execute().actionGet();

		SearchHit[] hits = searchResponse.getHits().getHits();
		ArticlesElasticResponse response = new ArticlesElasticResponse();
		List<ArticleElastic> articles = new ArrayList<ArticleElastic>();
		for (SearchHit hit : hits) {
			ArticleElastic article = objectMapper.readValue(hit.getSourceAsString(), ArticleElastic.class);
			articles.add(article);
		}
		response.setArticles(articles);
		response.setTook(searchResponse.getTookInMillis());
		response.setTotal(searchResponse.getHits().getTotalHits());
		List<Aggregation> aggregations = searchResponse.getAggregations().asList();
		ArticleElasticAggregations elasticAggs = new ArticleElasticAggregations();
		for(Aggregation aggregation : aggregations){
			if(aggregation.getName().equals("humanFiltered")){
				ArticleElasticAggregations aggs = objectMapper.readValue(XContentHelper.toString(aggregation), ArticleElasticAggregations.class);
				elasticAggs.setHumanFiltered(aggs.getHumanFiltered());
			}
			if(aggregation.getName().equals("medias")){
				ArticleElasticAggregations aggs = objectMapper.readValue(XContentHelper.toString(aggregation), ArticleElasticAggregations.class);
				elasticAggs.setMedias(aggs.getMedias());
			}
			if(aggregation.getName().equals("regions")){
				ArticleElasticAggregations aggs = objectMapper.readValue(XContentHelper.toString(aggregation), ArticleElasticAggregations.class);
				elasticAggs.setRegions(aggs.getRegions());
			}
			if(aggregation.getName().equals("outlets")){
				ArticleElasticAggregations aggs = objectMapper.readValue(XContentHelper.toString(aggregation), ArticleElasticAggregations.class);
				elasticAggs.setOutlets(aggs.getOutlets());
			}
		}
		response.setAggregations(elasticAggs);		
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
