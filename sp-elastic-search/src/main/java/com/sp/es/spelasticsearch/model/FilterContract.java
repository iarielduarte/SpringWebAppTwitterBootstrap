package com.sp.es.spelasticsearch.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterContract implements Serializable{
    
	List<String> categories;
    
    List<String> countries;
    
    List<Integer> dataGroupIds;
    
    List<String> outlets;
    
    List<Integer> mediaIds;
    
    List<StatusEnum> statuses;    
    
    private static final long serialVersionUID = 1L;

}
