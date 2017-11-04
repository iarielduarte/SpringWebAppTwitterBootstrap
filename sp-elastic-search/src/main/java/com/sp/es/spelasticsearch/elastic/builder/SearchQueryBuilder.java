package com.sp.es.spelasticsearch.elastic.builder;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

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
				.withQuery(query)
				.withPageable(pageable)
				.build();
		
		List<Article> articles =  elasticsearchTemplate.queryForList(build, Article.class);
		
		return articles;
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
