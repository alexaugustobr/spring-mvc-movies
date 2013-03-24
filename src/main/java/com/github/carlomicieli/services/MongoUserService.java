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

import com.github.carlomicieli.models.MailUser;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * @author Carlo Micieli
 */
@Service("userService")
public class MongoUserService implements UserService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public MongoUserService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public MailUser findUserById(ObjectId id) {
        return mongoTemplate.findById(id, MailUser.class);
    }

    public MailUser findUserByEmail(String emailAddress) {
        return mongoTemplate.findOne(new Query(where("emailAddress").is(emailAddress)), MailUser.class);
    }

    public void createUser(MailUser user) {
        mongoTemplate.insert(user);
    }
}
