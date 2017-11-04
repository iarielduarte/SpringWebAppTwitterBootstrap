package com.si.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import com.si.api.models.Article;

@SpringBootApplication
public class SecretInletApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecretInletApiApplication.class, args);
	}
	
	/**
	 * By default SDR not expose Ids, we can add more classes comma separated (Account.class, User.class);
	 * @return
	 */
	@Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {

        return new RepositoryRestConfigurerAdapter() {
            @Override
            public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
                config.exposeIdsFor(Article.class);
            }
        };
	}
}
