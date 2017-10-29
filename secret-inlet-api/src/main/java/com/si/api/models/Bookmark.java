package com.si.api.models;

/**
 * @author arielduarte
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="bookmarks")
public class Bookmark {

	@Id
	private String id;
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
