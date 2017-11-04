package com.sp.es.spelasticsearch.elastic.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sp.es.spelasticsearch.model.Article;

public interface ArticleElasticsearchService {
	
	Page<Article> findByPublicationName(String publicationName, Pageable pageable);
	
	Article findOne(String id);

}
