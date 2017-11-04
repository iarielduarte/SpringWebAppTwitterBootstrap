package com.sp.es.spelasticsearch.elastic.resoruce;

import static org.springframework.http.HttpStatus.OK;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.sp.es.spelasticsearch.elastic.builder.SearchQueryBuilder;
import com.sp.es.spelasticsearch.elastic.service.ArticleElasticsearchService;
import com.sp.es.spelasticsearch.elastic.utils.SearchResultArticle;
import com.sp.es.spelasticsearch.model.Article;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/rest/elastic/search/article")
public class SearchResource {
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Calendar cal = Calendar.getInstance();
	
	@Autowired
	ElasticsearchTemplate template;
	
	@Autowired
	private SearchQueryBuilder searchQueryBuilder;
	
	@Autowired
	private ArticleElasticsearchService service;
	
	@GetMapping(value="/term/{text}")
	public List<Article> searchByText(@PathVariable final String text){
		return searchQueryBuilder.getByText(text);
	}
	
	@GetMapping(value="/outlet", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getArticlesByOutlet(Pageable p){
		int realPage = 0;
		if (p.getPageNumber() > 0) {
            realPage = p.getPageNumber() - 1;
        }
        int size = 1;
        if (p.getPageSize() > 0) {
        	size = p.getPageSize();
        }
        Pageable pageRequest = new PageRequest(realPage, size);
		Map<String, Object> map = Maps.newHashMap();
		List<Article> articlesList = searchQueryBuilder.getByOutlet("Twitter", pageRequest);
		SearchResultArticle sra = new SearchResultArticle(articlesList.size(),size,realPage);
		map.put("SearchResultArticle", sra);
		map.put("Articles", articlesList);
		
		return new ResponseEntity<>(map, OK);
	}
	

	
	
	@GetMapping(value = "/delete-index")
    public boolean deleteIndex() {
        return template.deleteIndex(Article.class);
	}
	

	

}
