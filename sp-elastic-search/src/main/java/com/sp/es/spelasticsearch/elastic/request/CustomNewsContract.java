package com.sp.es.spelasticsearch.elastic.request;

import java.io.Serializable;
import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomNewsContract implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty(required = true)
	int companyId;
	
	@JsonProperty(required = true)
	int dataGroupID;

	@JsonProperty(required = true)
	String dataGroupName;

	//a.k.a 'body'
	@JsonProperty(required = true)
	String fullText;

	//a.k.a 'title'
	@JsonProperty(required = true)
	String headline;	
	
	//a.k.a 'language'
	@JsonProperty(required = false)
	String languageCode;
    
	@JsonProperty
	int mediaType;
	
	//a.k.a 'publicationName'
	@JsonProperty(required = true)
	String outlet;
	
	DateTime publicationDate;
	
	Boolean sampleSearchAdded;
	
	@JsonProperty(required = true)
	String searchId;
	
	String url;
	
	@JsonProperty(required = true)
	String username;
	
	@JsonProperty(required = false)
	List<String> collectionSourceTypes;

}
