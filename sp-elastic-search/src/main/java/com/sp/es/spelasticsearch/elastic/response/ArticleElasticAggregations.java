package com.sp.es.spelasticsearch.elastic.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(Include.NON_NULL)
public class ArticleElasticAggregations {
	
	ArticleElasticHumanFiltered humanFiltered;
	
	Buckets medias;
	
	Buckets regions;
	
	Buckets outlets;

}