package com.sp.es.spelasticsearch.model;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Field;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HumanFiltered {

    @Field("category")
    private String category;

	@Field("companyId")
	private Integer companyId;
	
	@Field("companyName")
	private String companyName;

	@Field("dataGroupId")
	private Integer dataGroupId;
	
	@Field("dataGroupName")
	private String dataGroupName;
	
	@Field("workspaceId")
	private Integer workspaceId;
	
	@Field("listId")
	private Integer listId;
	
	@Field("searchAgentName")
	private String searchAgentName;
	
	@Field("searchQuery")
	private String searchQuery;
	
	@Field("searchId")
	private String searchId;
	
	@Field("mediaMonitoringId")
	private String mediaMonitoringId;
	
	@Field("searchType")
	private String searchType;
	
	@Field("routingOption")
	private String routingOption;
	
	@Field("brief")
	private String brief;
	
	@Field("status")
	private StatusEnum status;
	
	@Field("confidencePercentage")
	private float confidencePercentage;
	
	@Field("publishedUser")
	private String publishedUser;
	
	@Field("publishedDate")
	private DateTime publishedDate;
	
	@Field("rejectedUser")
	private String rejectedUser;
	
	@Field("rejectedDate")
	private DateTime rejectedDate;
	
	@Field("showCreatedNotification")
	private boolean showCreatedNotification;
	
	@Field("showUpdatedNotification")
	private boolean showUpdatedNotification;
	
}
