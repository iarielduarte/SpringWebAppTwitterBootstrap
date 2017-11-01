package com.si.api.elastic.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.si.api.elastic.repository.BookmarkElasticsearchRepository;
import com.si.api.models.Bookmark;

@RestController
@RequestMapping("/rest/search")
public class SearchResource {
	
	@Autowired
	BookmarkElasticsearchRepository elaticRepository;
	
	@GetMapping(value="/name/{text}")
	public List<Bookmark> searchName(@PathVariable final String text){
		return elaticRepository.findByName(text);
	}
	
	@GetMapping(value="/name/{author}")
	public List<Bookmark> searchAuthor(@PathVariable final String text){
		return elaticRepository.findByAuthor(text);
	}
	
	@GetMapping(value="/all")
	public List<Bookmark> searchAll(){
		List<Bookmark> bookmarksList = new ArrayList<>();
		Iterable<Bookmark> ibookmarks = elaticRepository.findAll();
		ibookmarks.forEach(bookmarksList::add);
		
		return bookmarksList;
	}
	

}
