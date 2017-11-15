package com.sp.es.spelasticsearch.elastic.request;


import com.sp.es.spelasticsearch.elastic.enumerate.BulkOperationEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BulkContract {
    
	private BulkOperationEnum bulkOperation;
	
	private Integer[] dataGroupIds;

    private String[] vtkeys;

    private String user;

	
	
}
