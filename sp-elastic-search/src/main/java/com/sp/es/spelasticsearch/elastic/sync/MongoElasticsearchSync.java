package com.sp.es.spelasticsearch.elastic.sync;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sp.es.spelasticsearch.elastic.repository.ArticleElasticsearchRepository;
import com.sp.es.spelasticsearch.model.Article;
import com.sp.es.spelasticsearch.model.FilterContract;
import com.sp.es.spelasticsearch.repository.ArticleRepository;
import com.sp.es.spelasticsearch.service.ArticleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/rest/es/sync")
class MongoElasticsearchSync {
	
	@Autowired
	ElasticsearchOperations operations;
	
	@Autowired
	ArticleElasticsearchRepository elaticRepository;
	
	@Autowired
    private ArticleService articleService;
	
	@Autowired
	ArticleRepository mongoRepository;
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	
	@RequestMapping(value = "/articles", method = RequestMethod.POST)
    public @ResponseBody Page<Article> articleList(
            @RequestBody FilterContract filterContract, Pageable p) {
        int realPage = 0;
        if (p.getPageNumber() > 0) {
            realPage = p.getPageNumber() - 1;
        }
        int size = 1;
        if (p.getPageSize() > 0) {
        	size = p.getPageSize();
        }
        Sort sort = new Sort(Sort.Direction.DESC, "publishedOnDT");
        if(p.getSort()!=null){
            sort = p.getSort();
        }
        Pageable pageRequest = new PageRequest(realPage, size, sort);
        Page<Article> pageResults = articleService.filterArticle(filterContract, pageRequest, false);
        
        return pageResults;
    }
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	private List<Article> SyncDatatoES() {
		List<Article> articles = new ArrayList<Article>();
		log.info("*Start get all articles" + dateFormat.format(Calendar.getInstance().getTime()));
		Iterable<Article> iarticles = mongoRepository.findAll();
		log.info("*Completed get all articles" + dateFormat.format(Calendar.getInstance().getTime()));
		iarticles.forEach(articles::add);
		loadAll(articles);
		return articles;
	}

	@Transactional
	public void loadAll(List<Article> articles){
		operations.putMapping(Article.class);
		log.info("Start sync data" + dateFormat.format(Calendar.getInstance().getTime()));
		elaticRepository.save(articles);
		log.info("Sync completed" + dateFormat.format(Calendar.getInstance().getTime()));
	}
}
