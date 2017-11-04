package com.sp.es.spelasticsearch.elastic.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.sp.es.spelasticsearch.model.Article;


public interface ArticleElasticsearchRepository extends ElasticsearchRepository<Article, String>{

	Page<Article> findByPublicationName(String publicationName, Pageable pageable);
}
