package com.si.api.elastic.model;



/**
 * @author arielduarte
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.si.api.models.Tags;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName="bookmarks", type="bookmarks", shards=5	)
public class BookmarkTest {

	private Long id;
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
