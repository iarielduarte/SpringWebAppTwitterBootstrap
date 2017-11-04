package com.sp.es.spelasticsearch.elastic.utils;

import org.joda.time.DateTime;

import com.fasterxml.jackson.datatype.joda.cfg.FormatConfig;
import com.fasterxml.jackson.datatype.joda.cfg.JacksonJodaDateFormat;
import com.fasterxml.jackson.datatype.joda.deser.DateTimeDeserializer;

public class MyDateTimeDeserializer extends DateTimeDeserializer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyDateTimeDeserializer() {
		super(DateTime.class, FormatConfig.DEFAULT_DATETIME_PARSER);
		// TODO Auto-generated constructor stub
	}

	
	
	

}
