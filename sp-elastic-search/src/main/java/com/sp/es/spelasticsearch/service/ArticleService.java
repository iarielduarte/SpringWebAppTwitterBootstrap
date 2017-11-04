package com.sp.es.spelasticsearch.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sp.es.spelasticsearch.model.Article;
import com.sp.es.spelasticsearch.model.FilterContract;


public interface ArticleService {
	
	public Page<Article> filterArticle(FilterContract filterContract, Pageable pageRequest, boolean validation);

}
