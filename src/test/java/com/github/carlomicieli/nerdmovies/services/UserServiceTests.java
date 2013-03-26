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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * @author Carlo Micieli
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {
    @Mock
    private MongoTemplate mockMongo;

    @InjectMocks
    private MongoUserService userService;

    @Before
    public void setUp() {
        //This method has to be called to initialize annotated fields.
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findUserById() {
        ObjectId id = new ObjectId("47cc67093475061e3d95369d");

        when(mockMongo.findById(eq(id), eq(MailUser.class))).thenReturn(new MailUser());

        MailUser user = userService.findUserById(id);
        assertNotNull("User not found", user);

        verify(mockMongo, times(1)).findById(eq(id), eq(MailUser.class));
    }

    @Test
    public void findUserByEmailAddress() {
        when(mockMongo.findOne(isA(Query.class), eq(MailUser.class))).thenReturn(new MailUser());

        MailUser user = userService.findUserByEmail("joey@ramones.com");
        assertNotNull("User not found", user);

        verify(mockMongo, times(1)).findOne(isA(Query.class), eq(MailUser.class));
    }

    @Test
    public void createUser() {
        MailUser user = new MailUser();
        userService.createUser(user);
        verify(mockMongo, times(1)).insert(eq(user));
    }
}
