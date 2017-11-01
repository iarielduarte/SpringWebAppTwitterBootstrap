package com.si.api.models;

/**
 * @author arielduarte
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import org.springframework.data.annotation.Id;



@Data
@AllArgsConstructor
@NoArgsConstructor
@org.springframework.data.mongodb.core.mapping.Document(collection = "bookmarks")
@org.springframework.data.elasticsearch.annotations.Document(indexName="bookmarks", type="bookmarks", shards=5)
public class Bookmark {

	@Id
	private String bookmarkId;
	private String name;
	private String author;
	private String description;
	private String url;
	private String type;
	private String code;
	private String image;
	private String category;
	private List<Tags> tags;
	private String createdDT;
}
