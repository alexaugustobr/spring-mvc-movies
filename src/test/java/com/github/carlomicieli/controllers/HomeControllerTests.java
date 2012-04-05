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
package com.github.carlomicieli.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.github.carlomicieli.services.MovieService;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * 
 * @author Carlo P. Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTests {
	@Mock private Model mockModel;
	@Mock private MovieService mockService;
	@InjectMocks private HomeController homeController;
	
	@Before
	public void setUp() {
		//This method has to be called to initialize annotated fields.
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void indexShouldProduceTheCorrectViewName() {
		String viewName = homeController.index(mockModel);
		assertEquals("home/index", viewName);
	}
	
	@Test
	public void shouldFillTheListOfRecentMovies() {
		ExtendedModelMap model = new ExtendedModelMap();
		homeController.index(model);
		assertNotNull("List of movies is empty", model.get("movies"));
		
		verify(mockService, times(1)).getRecentMovies(10);
	}
	
	@Test
	public void aboutShouldProduceTheCorrectViewName() {
		String viewName = homeController.about();
		assertEquals("home/about", viewName);
	}
}
