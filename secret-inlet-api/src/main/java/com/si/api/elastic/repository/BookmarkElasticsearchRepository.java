package com.si.api.elastic.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.si.api.models.Article;

public interface BookmarkElasticsearchRepository extends ElasticsearchRepository<Article, String>{
	
	List<Article> findByName(String text);
	
	List<Article> findByAuthor(String text);

}
