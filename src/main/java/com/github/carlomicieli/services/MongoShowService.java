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
package com.github.carlomicieli.services;

import com.github.carlomicieli.models.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * A shows service for Mongodb.
 *
 * @author Carlo Micieli
 */
@Service("showService")
public class MongoShowService implements ShowService {
    private MongoTemplate mongoTemplate;

    @Autowired
    public MongoShowService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void create(Show s) {
        mongoTemplate.insert(s);
    }

    public List<Show> getAllShows() {
        return mongoTemplate.find(new Query(), Show.class);
    }
}
