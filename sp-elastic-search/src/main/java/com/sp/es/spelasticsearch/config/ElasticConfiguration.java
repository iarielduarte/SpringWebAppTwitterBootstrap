package com.sp.es.spelasticsearch.config;




import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

@Configuration
public class ElasticConfiguration {

	/**
	 * Configuration with a remote client  
	 */
	@Bean
	public Client client() throws UnknownHostException{
		String hostname = "127.0.0.1";
		int port = 9200; 
					
		Settings settings = Settings.builder()
				.put("cluster.name", "elasticsearch")
				.build();
		TransportClient client = TransportClient.builder().settings(settings).build()
		.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostname), port));
		
	    return client;
	}

	@Bean
	public ElasticsearchOperations elasticsearchTemplate() throws UnknownHostException{
	    return new ElasticsearchTemplate(client());
	}
	
	/**
	 * Configuration with a Node Builder 
	 */
	/*
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
	                        .put("index.number_of_shards", "1")
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
	*/
}
