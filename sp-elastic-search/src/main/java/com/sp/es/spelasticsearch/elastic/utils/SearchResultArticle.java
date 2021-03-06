package com.sp.es.spelasticsearch.elastic.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResultArticle {
	private Integer total;
	private Integer size;
    private Integer page;
    private Integer totalPage;
    private double totalUnread;
    private double totalheld;
    private double totalReadyToPublish;
    private double totalPublished;
    private double totalRejected;
}
