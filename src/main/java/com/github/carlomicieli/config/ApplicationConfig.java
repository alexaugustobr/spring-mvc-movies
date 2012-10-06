/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.carlomicieli.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.github.carlomicieli.models.MovieBeforeSaveListener;
import com.github.carlomicieli.services.MongoMovieService;
import com.github.carlomicieli.services.MovieService;
import com.mongodb.WriteConcern;

/**
 * The configuration class for the Spring application context.
 * @author Carlo P. Micieli
 */
@Configuration
@Import({ProductionConfiguration.class, ComponentTestConfig.class})
@ComponentScan(basePackages = "com.github.carlomicieli")
@ImportResource("classpath:META-INF/spring/security.xml")
public class ApplicationConfig {
	
	/**
	 * Return the message source for multi-language management.
	 * @return the message source bean.
	 */
	public @Bean MessageSource messageSource() {
		ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
		ms.setBasenames(new String[] { "locale/Messages", "locale/Errors" });
		return ms;
	}
	
	/**
	 * Return the saving listener used to hooks an event before movies are persisted.
	 * @return the movie before save listener bean. 
	 */
	public @Bean MovieBeforeSaveListener movieBeforeSaveListener() {
		return new MovieBeforeSaveListener();
	}
	
	/**
	 * Return the movie service.
	 * @return the movie service bean.
	 */
	public @Bean MovieService movieService() {
		return new MongoMovieService();
	}
	
	private @Autowired MongoDbFactory mongoDbFactory;
	
	/**
	 * Return the MongoDB template.
	 * @return the MongoDB template bean.
	 * @throws Exception
	 */
	public @Bean MongoTemplate mongoTemplate() throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory);
		mongoTemplate.setWriteConcern(WriteConcern.SAFE);
		return mongoTemplate;
	}
}
