package com.sp.es.spelasticsearch.elastic.builder;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filters.Filters;
import org.elasticsearch.search.aggregations.bucket.filters.Filters.Bucket;
import org.elasticsearch.search.aggregations.bucket.filters.InternalFilters;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.sum.Sum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.sp.es.spelasticsearch.elastic.utils.SearchResultArticle;
import com.sp.es.spelasticsearch.model.Article;



@Component
public class SearchQueryBuilder {
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	public List<Article> getByOutlet(String outlet, Pageable pageable) {
		QueryBuilder query = QueryBuilders.boolQuery()
				.should(
						QueryBuilders.queryStringQuery(outlet)
						.lenient(true)
						.field("publicationName"));
		
		NativeSearchQuery build = new NativeSearchQueryBuilder()
				.withPageable(pageable)
				.withQuery(query)
				.build();
		
		List<Article> articles =  elasticsearchTemplate.queryForList(build, Article.class);
		
		return articles;
	}
		
	public Map<String, Long> getStatusAggregation(String outlet){
		QueryBuilder query = QueryBuilders.boolQuery()
				.should(
						QueryBuilders.queryStringQuery(outlet)
						.lenient(true)
						.field("publicationName")
						);
		
		NativeSearchQuery build = new NativeSearchQueryBuilder()
				.withIndices("articles")
				.withTypes("articles")
				.withQuery(query)
				.addAggregation(AggregationBuilders.terms("status").field("humanFiltered.status"))
				.build();
		
		Aggregations aggregations = elasticsearchTemplate.query(build, new ResultsExtractor<Aggregations>() {
			@Override
			  public Aggregations extract(SearchResponse response) {
			    return response.getAggregations();
			  }
			});
		Map<String, Long> buckets = Maps.newHashMap();;
		Terms statusTerms = (Terms) aggregations.get("status");
		statusTerms.getBuckets().forEach(
			      bucket -> {
			    	  System.out.println(String.format("Key: %s, Doc count: %d", bucket.getKey(), bucket.getDocCount()));
			    	  buckets.put(bucket.getKey().toString(), bucket.getDocCount());
			        
			      });
		return buckets;
	}
	

	public List<Article> getByText(String text) {
		QueryBuilder query = QueryBuilders.boolQuery()
				.should(
						QueryBuilders.queryStringQuery(text)
						.lenient(true)
						.field("title")
						.field("author")
						.field("vtKey")
				).should(QueryBuilders.queryStringQuery("*"+text+"*")
						.lenient(true)
						.field("title")
						.field("author")
				);
		
		NativeSearchQuery build = new NativeSearchQueryBuilder()
				.withQuery(query)
				.build();
		
		List<Article> articles =  elasticsearchTemplate.queryForList(build, Article.class);
		
		return articles;
	}
	
	

}
