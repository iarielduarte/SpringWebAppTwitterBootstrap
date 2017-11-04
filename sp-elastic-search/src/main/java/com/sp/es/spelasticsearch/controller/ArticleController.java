package com.sp.es.spelasticsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sp.es.spelasticsearch.model.Article;
import com.sp.es.spelasticsearch.model.FilterContract;
import com.sp.es.spelasticsearch.service.ArticleService;

@RestController
@RequestMapping("/rest/search")
public class ArticleController {
	
	@Autowired
    private ArticleService articleService;
	
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

	
}
