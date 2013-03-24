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
package com.github.carlomicieli.controllers;

import com.github.carlomicieli.test.AbstractSpringControllerTests;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author Carlo Micieli
 */
public class HomeControllerMappingTests extends AbstractSpringControllerTests {

    @Test
    public void shouldRenderHomepage() throws Exception {
        login();

        mockMvc().perform(get("/"))
                .andDo(print())
                .andExpect(model().attributeExists("movies"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/index"));
    }

    @Test
    public void shouldRenderAboutPage() throws Exception {
        login();

        mockMvc().perform(get("/about"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("home/about"));
    }
}
