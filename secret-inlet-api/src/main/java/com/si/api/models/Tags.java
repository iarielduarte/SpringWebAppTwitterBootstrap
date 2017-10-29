package com.si.api.models;


import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

public class Tags {
		
	@Setter
	@Getter
	@Field("text")
	private String text;

}
