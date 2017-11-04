package com.si.api.config;



import java.io.File;
import java.io.IOException;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages="com.si.api.repository")
@EnableElasticsearchRepositories(basePackages="com.si.api.elastic.repository")
public class ElasticConfiguration {

//	private TransportClient client;
	
//	@Bean
//	public TransportClient client() throws UnknownHostException{
//		
//		String hostname = "https://h7kp3ulr:tt9fya2v22eppyoa@box-7630165.us-east-1.bonsaisearch.net";
//		int port = 443; 
//			
//		Settings settings = Settings.builder()
//				.put("cluster.name", "elasticsearch")
//				.put("client.transport.nodes_sampler_interval", "5s")
//	            .put("client.transport.sniff", false)
//	            .put("transport.tcp.compress", true)
//	            .put("xpack.security.transport.ssl.enabled", true)
//				.build();
//		client = new PreBuiltTransportClient(settings);
//		client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9200));
//		
//	    return client;
//	}
//
//	@Bean
//	public ElasticsearchOperations elasticsearchTemplate() throws UnknownHostException{
//	    return new ElasticsearchTemplate(client());
//	}
	
	/**
	 * Example to ElasticsearchTemplate of local file node
	 */
	
	@Bean
    public NodeBuilder nodeBuilder() {
        return new NodeBuilder();
	}
	
	 @Bean
	 public ElasticsearchOperations elasticsearchTemplate() throws IOException {
	        File tmpDir = File.createTempFile("elastic", Long.toString(System.nanoTime()));
	        System.out.println("Temp directory: " + tmpDir.getAbsolutePath());
	        Settings.Builder elasticsearchSettings =
	                Settings.settingsBuilder()
	                        .put("http.enabled", "true") // 1
	                        .put("index.number_of_shards", "5")
	                        .put("path.data", new File(tmpDir, "data").getAbsolutePath()) // 2
	                        .put("path.logs", new File(tmpDir, "logs").getAbsolutePath()) // 2
	                        .put("path.work", new File(tmpDir, "work").getAbsolutePath()) // 2
	                        .put("path.home", tmpDir); // 3



	        return new ElasticsearchTemplate(nodeBuilder()
	                .local(true)
	                .settings(elasticsearchSettings.build())
	                .node()
	                .client());
	}
	
}
