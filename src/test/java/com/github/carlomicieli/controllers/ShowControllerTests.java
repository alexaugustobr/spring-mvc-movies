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
import org.springframework.validation.BindingResult;

import com.github.carlomicieli.models.Show;
import com.github.carlomicieli.security.MailUserDetails;
import com.github.carlomicieli.security.SecurityService;
import com.github.carlomicieli.services.ShowService;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * 
 * @author Carlo P. Micieli
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ShowControllerTests {
	@Mock private BindingResult mockBinding;
	@Mock private Model mockModel;
	@Mock private SecurityService securityService;
	@Mock private ShowService mockService;
	@InjectMocks private ShowController showController;
	
	@Before
	public void setUp() {
		//This method has to be called to initialize annotated fields.
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldInitializeCreateForm() {
		ExtendedModelMap model = new ExtendedModelMap();
		
		String viewName = showController.createShow(model);
		assertEquals("show/new", viewName);
		assertNotNull(model.get("show"));
	}
	
	@Test
	public void shouldRedirectToCreateFormAfterErrors() {
		when(mockBinding.hasErrors()).thenReturn(true);
		Show show = new Show();
		
		String viewName = showController.save(show, mockBinding, mockModel);
		
		assertEquals("show/new", viewName);
		verify(mockModel, times(1)).addAttribute(eq(show));
	}
	
	@Test
	public void shouldSaveShows() {
		Show show = new Show();
		MailUserDetails user = new MailUserDetails("joey", "secret");
		when(securityService.getCurrentUser()).thenReturn(user);
		when(mockBinding.hasErrors()).thenReturn(false);
				
		String viewName = showController.save(show, mockBinding, mockModel);
		
		assertEquals("joey", show.getHostedBy());
		assertEquals("show/view", viewName);
		verify(mockService, times(1)).create(eq(show));
	}
}
