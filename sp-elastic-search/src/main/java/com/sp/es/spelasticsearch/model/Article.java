package com.sp.es.spelasticsearch.model;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.sp.es.spelasticsearch.elastic.utils.MyDateTimeDeserializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@Document(collection = "Article")
@org.springframework.data.elasticsearch.annotations.Document(indexName="orionscion", type="Article")
public class Article {

	@Id
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
//    private DateTime embargoedDT;
//    private DateTime embargoLiftedDT;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private DateTime foundDT;
    @Field(type = FieldType.Nested)
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
//    @JsonSerialize(using = DateTimeSerializer.class)
//    @JsonDeserialize(using=MyDateTimeDeserializer.class)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private DateTime orionScionDT;
    private String permalink;
    private String publicationName;
    private String publicationNameLowercase;
//    @JsonSerialize(using = DateTimeSerializer.class)
//    @JsonDeserialize(using=MyDateTimeDeserializer.class)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    private DateTime publishedOnDT;
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
