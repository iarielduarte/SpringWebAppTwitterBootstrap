package com.sp.es.spelasticsearch.config;

import java.net.InetAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ElasticConfiguration {
	
	@Value("${elasticsearch.host}")
	private String EsHost;

	@Value("${elasticsearch.port}")
	private int EsPort;

	@Value("${elasticsearch.clustername}")
	private String EsClusterName;

	@Bean
	public Client client() throws Exception {
		Settings esSettings = Settings.builder().put("cluster.name", EsClusterName).build();
		TransportClient client = new PreBuiltTransportClient(esSettings);
		client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(EsHost), EsPort));
		return client;
	}
	
}
