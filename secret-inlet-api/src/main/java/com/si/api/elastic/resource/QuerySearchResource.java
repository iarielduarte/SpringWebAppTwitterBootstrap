package com.si.api.elastic.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.si.api.elastic.builder.SearchQueryBuilder;
import com.si.api.models.Article;

@RestController
@RequestMapping("/rest/search/query")
public class QuerySearchResource {
	
	 
	@Autowired
	private SearchQueryBuilder searchQueryBuilder;
	
	@GetMapping(value="/{text}")
	public List<Article> searchByText(@PathVariable final String text){
		return searchQueryBuilder.getAll(text);
	}
	

	

}
