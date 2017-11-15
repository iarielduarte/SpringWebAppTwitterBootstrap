package com.sp.es.spelasticsearch.elastic.response;

import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.sp.es.spelasticsearch.model.HumanFiltered;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(Include.NON_NULL)
public class ArticleElastic {

	private String id;
	private Boolean active;
    private String author;
    private Integer bestCountry;
    private String body;    
    private List<String> collectionSourceTypes;
    private Integer cpreMediaType;
    private String dmaId;
    private String domain;
    private Boolean embargoed;
    private DateTime embargoedDT;
    private DateTime embargoLiftedDT;
    private DateTime foundDT;
    private List<HumanFiltered> humanFiltered;
    private String imageServiceLink;
    private String mediaGroup;
    private Integer mediaId;
    private String mediaType;
    private String monitorSearchType;
    private String nodAdditionalInfo;
    private Integer nodContentID;
    private String nodMediaOutletCHSID;
    private List<String> nodRoutingIds;
    private String originalAuthor;
    private DateTime orionScionDT;
    private String permalink;
    private String publicationName;
    private String publicationNameLowercase;
    private DateTime publishedOnDT;
    private Integer reach;
    private String remotePostID;
    private Boolean sampleSearchAdded;
    private Integer sampling;
    private String site;
    private List<String> sourceId;
    private String subDomain;
    private String title;
    private String titleLowercase;
    private String vtKey;
	
}
