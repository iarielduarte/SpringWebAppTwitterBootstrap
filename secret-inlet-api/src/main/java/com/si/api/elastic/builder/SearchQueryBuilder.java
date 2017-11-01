package com.si.api.elastic.builder;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;


import com.si.api.models.Bookmark;

@Component
public class SearchQueryBuilder {
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	public List<Bookmark> getAll(String text) {
		QueryBuilder query = QueryBuilders.boolQuery()
				.should(
						QueryBuilders.queryStringQuery(text)
						.lenient(true)
						.field("name")
						.field("author")
						.field("category")
				).should(QueryBuilders.queryStringQuery("*"+text+"*")
						.lenient(true)
						.field("name")
						.field("author")
						.field("category")
				);
		
		NativeSearchQuery build = new NativeSearchQueryBuilder()
				.withQuery(query)
				.build();
		
		List<Bookmark> bookmarks =  elasticsearchTemplate.queryForList(build, Bookmark.class);
		
		return bookmarks;
	}
	
	

}
