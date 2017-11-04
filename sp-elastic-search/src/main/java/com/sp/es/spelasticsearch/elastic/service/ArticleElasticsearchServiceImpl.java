package com.sp.es.spelasticsearch.elastic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sp.es.spelasticsearch.elastic.repository.ArticleElasticsearchRepository;
import com.sp.es.spelasticsearch.model.Article;

@Service
public class ArticleElasticsearchServiceImpl implements ArticleElasticsearchService{

	@Autowired
	ArticleElasticsearchRepository elaticRepository;

	
	@Override
	public Page<Article> findByPublicationName(String publicationName, Pageable pageable) {
		return elaticRepository.findByPublicationName(publicationName, pageable);
	}


	@Override
	public Article findOne(String id) {
		return elaticRepository.findOne(id);
	}

}
