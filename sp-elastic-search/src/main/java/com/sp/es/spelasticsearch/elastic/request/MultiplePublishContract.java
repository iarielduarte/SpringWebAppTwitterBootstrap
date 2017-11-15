package com.sp.es.spelasticsearch.elastic.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sp.es.spelasticsearch.model.StatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultiplePublishContract {
    
    @JsonProperty("vtkey")
    private String vtkey;
    
    @JsonProperty("searchid")
    private String searchId;
    
    @JsonProperty("status")
    private StatusEnum status;
    
    @JsonProperty("user")
    private String user;
    
    @JsonProperty("showCreateNotification")
    private boolean showCreateNotification;
    
    @JsonProperty("showUpdateNotification")
    private boolean showUpdateNotification;
    

}
