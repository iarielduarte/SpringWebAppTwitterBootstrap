package com.si.api.elastic.load;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.si.api.elastic.model.Bookmark;
import com.si.api.elastic.repository.BookmarkElasticsearchRepository;

@Component
public class Loaders {

	@Autowired
	ElasticsearchOperations operations;
	
	@Autowired
	BookmarkElasticsearchRepository elaticRepository;
	
	@PostConstruct
	@Transactional
	public void loadAll(){
		operations.putMapping(Bookmark.class);
		System.out.println("Loading data");
		elaticRepository.save(getData());
		System.out.println("Loading completed");
	}

	private List<Bookmark> getData() {
		List<Bookmark> bookmarks = new ArrayList<Bookmark>();
		bookmarks.add(new Bookmark(123L,"Elastic", 
				"Ariel", 
				"Elasticsearch Testing",
				"https://www.elastic.co/", 
				"blog",	null, null, null, null, null));
		return bookmarks;
	}
}
