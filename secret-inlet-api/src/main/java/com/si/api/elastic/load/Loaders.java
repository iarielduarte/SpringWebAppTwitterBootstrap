package com.si.api.elastic.load;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.si.api.elastic.repository.BookmarkElasticsearchRepository;
import com.si.api.models.Bookmark;
import com.si.api.repository.BookmarkRepository;

@Component
public class Loaders {

	@Autowired
	ElasticsearchOperations operations;
	
	@Autowired
	BookmarkElasticsearchRepository elaticRepository;
	
	@Autowired
	BookmarkRepository mongoRepository;
	
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
		Iterable<Bookmark> ibookmarks = mongoRepository.findAll();
		
		ibookmarks.forEach(bookmarks::add);
		return bookmarks;
	}

//	private List<BookmarkTest> getData() {
//		List<BookmarkTest> bookmarks = new ArrayList<BookmarkTest>();
//		bookmarks.add(new BookmarkTest(123L,"ElasticSearch configuration for high performance", "Manoj Chaudhary", "High Performance...", "https://www.elastic.co/", "blog",	null, null, "Elasticsearch", null, null));
//		bookmarks.add(new BookmarkTest(124L,"Angular Is The Law", "Chris",	"Angular Code...", "https://www.angular.co/",	"blog",	null, null, "Angular", null, null));
//		bookmarks.add(new BookmarkTest(125L,"Spring Master", "Ariel",	"Spring app..", "https://www.srping.co/",	"blog",	null, null, "Srping", null, null));
//		bookmarks.add(new BookmarkTest(126L,"Node cool code", "Ariel",	"Node back...", "https://www.node.co/",	"blog",	null, null, "Node", null, null));
//		return bookmarks;
//	}
}
