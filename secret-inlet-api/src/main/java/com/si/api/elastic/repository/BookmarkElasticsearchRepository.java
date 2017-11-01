package com.si.api.elastic.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.si.api.models.Bookmark;

public interface BookmarkElasticsearchRepository extends ElasticsearchRepository<Bookmark, String>{
	
	List<Bookmark> findByName(String text);
	
	List<Bookmark> findByAuthor(String text);

}
