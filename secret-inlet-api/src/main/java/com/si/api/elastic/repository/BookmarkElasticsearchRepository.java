package com.si.api.elastic.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.si.api.elastic.model.Bookmark;

public interface BookmarkElasticsearchRepository extends ElasticsearchRepository<Bookmark, Long>{
	
	List<Bookmark> findByName(String text);
	
	List<Bookmark> findByAuthor(String text);

}
