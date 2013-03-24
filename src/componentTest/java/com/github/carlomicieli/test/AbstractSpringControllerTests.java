/*
 * Copyright 2013 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.github.carlomicieli.test;

import com.github.carlomicieli.config.ApplicationConfig;
import com.github.carlomicieli.config.ComponentTestConfig;
import com.github.carlomicieli.config.ProductionConfiguration;
import com.github.carlomicieli.config.WebConfig;
import com.github.carlomicieli.models.MailUser;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * This class ensures that a WebApplicationContext will be loaded for the
 * integration tests.
 *
 * @author Carlo Micieli
 */
@ContextConfiguration(classes = {ApplicationConfig.class,
        ProductionConfiguration.class,
        ComponentTestConfig.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public abstract class AbstractSpringControllerTests {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    protected MockMvc mockMvc() {
        return mockMvc;
    }

    protected void login() {
        login("mail@mail.com", "pa$$word");
    }

    protected void login(String emailAddress, String password) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new MailUser(emailAddress, password), null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
}
