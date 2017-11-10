package com.sp.es.spelasticsearch.elastic.builder;

import java.io.IOException;
import java.net.InetAddress;

import javax.annotation.PostConstruct;

import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.es.spelasticsearch.elastic.model.ArticleElastic;

//@Component
public class SearchQueryBuilder {
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	Client elasticSearchClient;
	

	@Value("${elasticsearch.indexname}")
	private String EsIndexName;

	@Value("${elasticsearch.type}")
	private String EsType;

	//@PostConstruct
	public void loadAll() throws Exception {
		searchByOutlet(elasticSearchClient);
	}
	
		
	
	private void searchByOutlet(Client client) {

		QueryBuilder query = boolQuery()
				.should(queryStringQuery("Twitter")
						.lenient(true)
						.field("publicationName")// outlet
		);

		SearchResponse searchResponse = client.prepareSearch(EsIndexName).setTypes(EsType).setQuery(query).execute()
				.actionGet();

		SearchHits hits = searchResponse.getHits();
		System.out.println(hits.getAt(0).getSource().get("body"));
		// hits.getTotalHits()
		// hits.getAt(0).getSource().get("mediaType")
	}

	private void search(Client client) throws Exception{
		
		QueryBuilder nestedQueries = boolQuery()
				.must(termQuery("humanFiltered.routingOption.keyword","HumanMonitoring"))
				.must(termQuery("humanFiltered.status.keyword","published"))
				.must(termQuery("humanFiltered.dataGroupId","13980922"))
				.must(termQuery("humanFiltered.category.keyword","PabloCat4192017"));
		
		QueryBuilder finalQuery = boolQuery()
				.must(termQuery("publicationNameLowercase.keyword","associated press"))
				.must(termQuery("bestCountry",6252001))
				.must(termQuery("cpreMediaType",119000))
				.must(termQuery("cpreMediaType",119000))
				.must(nestedQuery("humanFiltered", nestedQueries, ScoreMode.None));
		
		 
		
		SearchResponse searchResponse = client.prepareSearch(EsIndexName).setTypes(EsType).setQuery(finalQuery)
				.addAggregation(AggregationBuilders.terms("media").field("cpreMediaType"))
				.addAggregation(AggregationBuilders.terms("region").field("bestCountry"))
				.addAggregation(AggregationBuilders.terms("outlet").field("publicationNameLowercase.keyword"))
				//Nested Aggs
				.addAggregation(AggregationBuilders.nested("humanFilteredAggs", "humanFiltered")
						.subAggregation(AggregationBuilders.terms("status").field("humanFiltered.status.keyword"))
						.subAggregation(AggregationBuilders.terms("datagroup").field("humanFiltered.dataGroupId.keyword"))
						.subAggregation(AggregationBuilders.terms("category").field("humanFiltered.category.keyword")))
				.execute().actionGet();

		SearchHits hits = searchResponse.getHits();
		System.out.println(hits.getAt(0).getSource().get("body"));
		try {
			ArticleElastic article = objectMapper.readValue(hits.getAt(0).getSourceAsString(), ArticleElastic.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// hits.getTotalHits()
		// hits.getAt(0).getSource().get("mediaType")
	}

}
