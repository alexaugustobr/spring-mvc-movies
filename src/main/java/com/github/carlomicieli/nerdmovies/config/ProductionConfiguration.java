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
package com.github.carlomicieli.nerdmovies.config;

import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
 * Mongodb configuration for the production environment.
 *
 * @author Carlo Micieli
 */
@Configuration
@Profile("production")
@PropertySource("classpath:META-INF/spring/app.properties")
public class ProductionConfiguration {
    private
    @Autowired
    Environment env;

    /**
     * Return the mongodb factory for the production environment.
     *
     * @return the mongodb factory bean.
     * @throws Exception
     */
    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception {
        return new SimpleMongoDbFactory(
                new Mongo(env.getProperty("mongo.hostName"),
                        env.getProperty("mongo.portNumber", Integer.class)),
                env.getProperty("mongo.databaseName"));
    }
}