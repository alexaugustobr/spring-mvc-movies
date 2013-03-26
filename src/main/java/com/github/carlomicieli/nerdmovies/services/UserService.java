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
package com.github.carlomicieli.nerdmovies.services;

import com.github.carlomicieli.nerdmovies.models.MailUser;
import org.bson.types.ObjectId;

/**
 * @author Carlo Micieli
 */
public interface UserService {
    MailUser findUserById(ObjectId id);

    MailUser findUserByEmail(String emailAddress);

    void createUser(MailUser user);
}