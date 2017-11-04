package com.sp.es.spelasticsearch.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sp.es.spelasticsearch.model.Article;
import com.sp.es.spelasticsearch.model.FilterContract;
import com.sp.es.spelasticsearch.model.HumanFiltered;
import com.sp.es.spelasticsearch.model.StatusEnum;
import com.sp.es.spelasticsearch.repository.ArticleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService{

	@Autowired
    private ArticleRepository articleRepository;
	
	private static final List<StatusEnum> DEFAULT_STATUSES = Arrays.asList(StatusEnum.unread, StatusEnum.held, StatusEnum.rejected, StatusEnum.published,  StatusEnum.readyToPublish);
	
	private static final String INSIGHTS_ROUTE = "InsightsCoding";
	
	private static final String AUTOMATED_MONITORING = "AutomatedMonitoring";
	
	@Override
	public Page<Article> filterArticle(FilterContract filterContract, Pageable pageRequest, boolean validation) {

    	String countriesPresent = (filterContract.getCountries() != null && filterContract.getCountries().size() > 0) ? "1" : "0";
    	String categoriesPresent = (filterContract.getCategories() != null && filterContract.getCategories().size() > 0) ? "1" : "0";
    	String dgPresent = (filterContract.getDataGroupIds() != null && filterContract.getDataGroupIds().size() > 0) ? "1" : "0";
    	String outletPresent = (filterContract.getOutlets() != null && filterContract.getOutlets().size() > 0) ? "1" : "0";
    	String mediaIdsPresent = (filterContract.getMediaIds() != null && filterContract.getMediaIds().size() > 0) ? "1" : "0";
 
    	if (filterContract.getStatuses() == null || filterContract.getStatuses().size() == 0) {
      		filterContract.setStatuses(DEFAULT_STATUSES);
        }
        if(validation){
        	List<StatusEnum> statusesUpdated = new ArrayList<StatusEnum>();
        	for(StatusEnum item: filterContract.getStatuses()){
        		if(!item.equals(StatusEnum.published)){
        			statusesUpdated.add(item);
        		}
        	}
        	filterContract.setStatuses(statusesUpdated);
    	}
        Set<String> distinctOutlets = new HashSet<String>();
        if(outletPresent.equals("1")){
        	for(String outlet: filterContract.getOutlets()){
        		if(!outlet.trim().isEmpty())
        			distinctOutlets.add(outlet.toLowerCase());
        	}
        	filterContract.setOutlets(new ArrayList<String>(distinctOutlets));
        }
        Page<Article> pageResult = findByFilterContract(countriesPresent+categoriesPresent+dgPresent+outletPresent+mediaIdsPresent,filterContract, pageRequest);
        
    	List<Article> vtResults = new ArrayList<Article>();
        for(Article article: pageResult.getContent()){
        		removeUnmatchedHF(article, filterContract);
		        if (article.getHumanFiltered().size() > 0) vtResults.add(article);
        }
        return new PageImpl<Article>(vtResults, pageRequest, pageResult.getTotalElements());
        
        
    }
	
	private void removeUnmatchedHF(Article article, FilterContract filterContract){
    	if (article.getHumanFiltered()!=null && article.getHumanFiltered().size() > 1) {
            Iterator<HumanFiltered> humanFilteredIterator = article.getHumanFiltered().iterator();
            while (humanFilteredIterator.hasNext()) { 
                HumanFiltered hf = humanFilteredIterator.next();
                if (hf.getStatus()!=null && !filterContract.getStatuses().contains(hf.getStatus())) {
                	humanFilteredIterator.remove();
	            }else if(filterContract.getCategories() != null && filterContract.getCategories().size() > 0){
                	if (hf.getCategory()!=null && !filterContract.getCategories().contains(hf.getCategory())) {
	                	humanFilteredIterator.remove();
		            }
                }else if(filterContract.getDataGroupIds() != null && filterContract.getDataGroupIds().size() > 0){
                	if (hf.getDataGroupId()!=null && !filterContract.getDataGroupIds().contains(hf.getDataGroupId())) {
	                	humanFilteredIterator.remove();
		            }
                } else if(hf.getRoutingOption()!=null && (hf.getRoutingOption().equals(INSIGHTS_ROUTE) || hf.getRoutingOption().equals(AUTOMATED_MONITORING))){
                	humanFilteredIterator.remove();
                }
            }
        }
    }
	
	private Page<Article> findByFilterContract(String filtersPresent, FilterContract filterContract, Pageable pageRequest) {
    	List<Integer> countries = new ArrayList<Integer>();
        if(filterContract.getCountries() != null && filterContract.getCountries().size() > 0){
        	for(String countryId : filterContract.getCountries()){
        		try{
        			countries.add(Integer.valueOf(countryId));
        		}catch(NumberFormatException nfe){
        			log.error("invalid country id: " + countryId, nfe);
        		}
        	}
        }
    	
    	switch (filtersPresent) {
	        case "00000":
	            return articleRepository.findByStatuses(filterContract.getStatuses(), pageRequest);
	        case "00001":
	            return articleRepository.findByCpreMediaTypeAndStatuses(filterContract.getMediaIds(), filterContract.getStatuses(), pageRequest);
	        case "00010":
	            return articleRepository.findByPublicationNameAndStatuses(filterContract.getOutlets(), filterContract.getStatuses(), pageRequest);
	        case "00011":
	            return articleRepository.findByPublicationNameAndCpreMediaTypeAndStatuses(filterContract.getOutlets(), filterContract.getMediaIds(), filterContract.getStatuses(), pageRequest);
	        case "00100":
	            return articleRepository.findByDataGroupIdsAndStatuses(filterContract.getDataGroupIds(), filterContract.getStatuses(), pageRequest);
	        case "00101":
	            return articleRepository.findByDataGroupIdsAndCpreMediaTypeAndStatuses(filterContract.getDataGroupIds(), filterContract.getMediaIds(), filterContract.getStatuses(), pageRequest);
	        case "00110":
	            return articleRepository.findByDataGroupIdsAndPublicationNameAndStatuses(filterContract.getDataGroupIds(), filterContract.getOutlets(), filterContract.getStatuses(), pageRequest);
	        case "00111":
	            return articleRepository.findByDataGroupIdAndPublicationNameAndCpreMediaTypeAndStatuses(filterContract.getDataGroupIds(), filterContract.getOutlets(), filterContract.getMediaIds(), filterContract.getStatuses(), pageRequest);
	        case "01000":
	            return articleRepository.findByCategoriesAndStatuses(filterContract.getCategories(), filterContract.getStatuses(), pageRequest);
	        case "01001":
	            return articleRepository.findByCategoriesAndCpreMediaTypeAndStatuses(filterContract.getCategories(), filterContract.getMediaIds(), filterContract.getStatuses(), pageRequest);
	        case "01010":
	            return articleRepository.findByCategoriesAndPublicationNameAndStatuses(filterContract.getCategories(), filterContract.getOutlets(), filterContract.getStatuses(), pageRequest);
	        case "01011":
	            return articleRepository.findByCategoriesAndPublicationNameAndCpreMediaTypeAndStatuses(filterContract.getCategories(), filterContract.getOutlets(), filterContract.getMediaIds(), filterContract.getStatuses(), pageRequest);
	        case "01100":
	            return articleRepository.findByCategoriesAndDataGroupIdsAndStatuses(filterContract.getCategories(), filterContract.getDataGroupIds(), filterContract.getStatuses(), pageRequest);
	        case "01101":
	            return articleRepository.findByCategoriesAndDataGroupIdsAndCpreMediaTypeAndStatuses(filterContract.getCategories(), filterContract.getDataGroupIds(), filterContract.getMediaIds(), filterContract.getStatuses(), pageRequest);
	        case "01110":
	            return articleRepository.findByCategoriesAndDataGroupIdsAndPublicationNameAndStatuses(filterContract.getCategories(), filterContract.getDataGroupIds(), filterContract.getOutlets(), filterContract.getStatuses(), pageRequest);
	        case "01111":
	            return articleRepository.findByCategoriesAndDataGroupIdsAndPublicationNameAndCpreMediaTypeAndStatuses(filterContract.getCategories(), filterContract.getDataGroupIds(), filterContract.getOutlets(), filterContract.getMediaIds(), filterContract.getStatuses(), pageRequest);
	        case "10000":
	            return articleRepository.findByCountriesAndStatuses(countries, filterContract.getStatuses(), pageRequest);
	        case "10001":
	            return articleRepository.findByCountriesAndCpreMediaTypeAndStatuses(countries, filterContract.getMediaIds(), filterContract.getStatuses(), pageRequest);
	        case "10010":
	            return articleRepository.findByCountriesAndPublicationNameAndStatuses(countries, filterContract.getOutlets(), filterContract.getStatuses(), pageRequest);
	        case "10011":
	            return articleRepository.findByCountriesAndPublicationNameAndCpreMediaTypeAndStatuses(countries, filterContract.getOutlets(), filterContract.getMediaIds(), filterContract.getStatuses(), pageRequest);
	        case "10100":
	            return articleRepository.findByCountriesAndDataGroupIdsAndStatuses(countries, filterContract.getDataGroupIds(), filterContract.getStatuses(), pageRequest);
	        case "10101":
	            return articleRepository.findByCountriesAndDataGroupIdsAndCpreMediaTypeAndStatuses(countries, filterContract.getDataGroupIds(), filterContract.getMediaIds(), filterContract.getStatuses(), pageRequest);
	        case "10110":
	            return articleRepository.findByCountriesAndDataGroupIdsAndPublicationNameAndStatuses(countries, filterContract.getDataGroupIds(), filterContract.getOutlets(), filterContract.getStatuses(), pageRequest);
	        case "10111":
	            return articleRepository.findByCountriesAndDataGroupIdAndPublicationNameAndCpreMediaTypeAndStatuses(countries, filterContract.getDataGroupIds(), filterContract.getOutlets(), filterContract.getMediaIds(), filterContract.getStatuses(), pageRequest);
	        case "11000":
	            return articleRepository.findByCountriesAndCategoriesAndStatuses(countries, filterContract.getCategories(), filterContract.getStatuses(), pageRequest);
	        case "11001":
	            return articleRepository.findByCountriesAndCategoriesAndCpreMediaTypeAndStatuses(countries, filterContract.getCategories(), filterContract.getMediaIds(), filterContract.getStatuses(), pageRequest);
	        case "11010":
	            return articleRepository.findByCountriesAndCategoriesAndPublicationNameAndStatuses(countries, filterContract.getCategories(), filterContract.getOutlets(), filterContract.getStatuses(), pageRequest);
	        case "11011":
	            return articleRepository.findByCountriesAndCategoriesAndPublicationNameAndCpreMediaTypeAndStatuses(countries, filterContract.getCategories(), filterContract.getOutlets(), filterContract.getMediaIds(), filterContract.getStatuses(), pageRequest);
	        case "11100":
	            return articleRepository.findByCountriesAndCategoriesAndDataGroupIdsAndStatuses(countries, filterContract.getCategories(), filterContract.getDataGroupIds(), filterContract.getStatuses(), pageRequest);
	        case "11101":
	            return articleRepository.findByCountriesAndCategoriesAndDataGroupIdsAndCpreMediaTypeAndStatuses(countries, filterContract.getCategories(), filterContract.getDataGroupIds(), filterContract.getMediaIds(), filterContract.getStatuses(), pageRequest);
	        case "11110":
	            return articleRepository.findByCountriesAndCategoriesAndDataGroupIdsAndPublicationNameAndStatuses(countries, filterContract.getCategories(), filterContract.getDataGroupIds(), filterContract.getOutlets(), filterContract.getStatuses(), pageRequest);
	        case "11111":
	            return articleRepository.findByCountriesAndCategoriesAndDataGroupIdsAndPublicationNameAndCpreMediaTypeAndStatuses(countries, filterContract.getCategories(), filterContract.getDataGroupIds(), filterContract.getOutlets(), filterContract.getMediaIds(), filterContract.getStatuses(), pageRequest);
	        default:
	            return null;
    	}
	}

}
