package com.sp.es.spelasticsearch.elastic.resoruce;

import java.io.IOException;

import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sp.es.spelasticsearch.elastic.model.ArticlesElasticResponse;
import com.sp.es.spelasticsearch.elastic.service.ArticleElasticsearchService;
import com.sp.es.spelasticsearch.model.FilterContract;

@RestController
@RequestMapping("/rest/elastic/search/article")
public class SearchResourceController {
	
	@Autowired
	ArticleElasticsearchService elasticService;
	
	@RequestMapping(value = "/elasticSearch", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ArticlesElasticResponse get(@RequestBody FilterContract filterContract,
    		Pageable p) throws JsonParseException, JsonMappingException, IOException {
		int realPage = 0;
        if (p.getPageNumber() > 0) {
            realPage = p.getPageNumber() - 1;
        }
        Sort sort = new Sort(Sort.Direction.DESC, "publishedOnDT");
        if(p.getSort()!=null){
            sort = p.getSort();
        }
        Pageable pageRequest = new PageRequest(realPage, 1, sort);
		ArticlesElasticResponse articles = elasticService.findByStatus(filterContract,pageRequest);
		return articles;
    }
	
	@GetMapping(value="/outlet/{outlet}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void getArticlesByOutlet(@PathVariable final String outlet){
		
		SearchHits hits = elasticService.searchByOutlet(outlet).getHits();
		System.out.println(hits.getAt(0).getSource().get("body"));
	}

}
