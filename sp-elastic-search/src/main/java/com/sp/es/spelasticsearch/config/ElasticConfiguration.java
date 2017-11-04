package com.sp.es.spelasticsearch.config;

import java.net.InetAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.sp.es.spelasticsearch.elastic.utils.ConfigurableEntityMapper;

@Configuration
@EnableMongoRepositories(basePackages="com.sp.es.spelasticsearch.repository")
@EnableElasticsearchRepositories(basePackages="com.sp.es.spelasticsearch.elastic.repository")
public class ElasticConfiguration {
	
	@Value("${elasticsearch.host}")
    private String EsHost;

    @Value("${elasticsearch.port}")
    private int EsPort;

    @Value("${elasticsearch.clustername}")
    private String EsClusterName;
    
    @Bean
    public EntityMapper mapper() {
    	return new ConfigurableEntityMapper();
    }

	/**
	 * Transport Client Configuration   
	 */
	@Bean
	public Client client() throws Exception{
		Settings esSettings = Settings.settingsBuilder()
                .put("cluster.name", EsClusterName)
                .build(); 
					
		//https://www.elastic.co/guide/en/elasticsearch/guide/current/_transport_client_versus_node_client.html
        return TransportClient.builder()
                .settings(esSettings)
                .build()
                .addTransportAddress(
				  new InetSocketTransportAddress(InetAddress.getByName(EsHost), EsPort));
	}

	@Bean
	public ElasticsearchOperations elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(client(), mapper());
    }
	
	/**
	 * Configuration with a Node Builder 
	 */
	
//	@Bean
//    public NodeBuilder nodeBuilder() {
//        return new NodeBuilder();
//	}
//	
//	 @Bean
//	 public ElasticsearchOperations elasticsearchTemplate() throws IOException {
//	        File tmpDir = File.createTempFile("elastic", Long.toString(System.nanoTime()));
//	        System.out.println("Temp directory: " + tmpDir.getAbsolutePath());
//	        Settings.Builder elasticsearchSettings =
//	                Settings.settingsBuilder()
//	                        .put("http.enabled", "true") // 1
//	                        .put("index.number_of_shards", "5")
//	                        .put("path.data", new File(tmpDir, "data").getAbsolutePath()) // 2
//	                        .put("path.logs", new File(tmpDir, "logs").getAbsolutePath()) // 2
//	                        .put("path.work", new File(tmpDir, "work").getAbsolutePath()) // 2
//	                        .put("path.home", tmpDir); // 3
//
//
//
//	        return new ElasticsearchTemplate(nodeBuilder()
//	                .local(true)
//	                .settings(elasticsearchSettings.build())
//	                .node()
//	                .client(), mapper());
//	}
//	 
	
	
}
