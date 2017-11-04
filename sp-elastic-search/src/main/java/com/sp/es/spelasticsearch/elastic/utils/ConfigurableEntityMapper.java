package com.sp.es.spelasticsearch.elastic.utils;

import java.io.IOException;

import org.springframework.data.elasticsearch.core.EntityMapper;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.joda.JodaModule;

public class ConfigurableEntityMapper implements EntityMapper{

	 public ConfigurableEntityMapper() {
	        ObjectMapper mapper = new ObjectMapper(this.jsonFactory());
	        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
	        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	        mapper.setSerializationInclusion(Include.NON_NULL);
	        mapper.registerModule(new JodaModule());
	        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	        mapper.setDateFormat(new ISO8601DateFormat());
	    }
	 
	  public JsonFactory jsonFactory() {
	        JsonFactory jsonFactory = new JsonFactory();
	        jsonFactory.enable(Feature.ALLOW_COMMENTS);
	        jsonFactory.enable(Feature.ALLOW_SINGLE_QUOTES);
	        jsonFactory.enable(Feature.ALLOW_UNQUOTED_CONTROL_CHARS);
	        return jsonFactory;
	    }
	 
	@Override
	public String mapToString(Object object) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T mapToObject(String source, Class<T> clazz) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
   

}
