/*
Copyright 2012 the original author or authors.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.github.carlomicieli.controllers;

import com.github.carlomicieli.models.MailUser;
import com.github.carlomicieli.security.SecurityService;
import com.github.carlomicieli.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * @author Carlo Micieli
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthControllerTests {
    @Mock
    private Model mockModel;
    @Mock
    private UserService mockService;
    @Mock
    private SecurityService mockSecService;
    @Mock
    private PasswordEncoder mockEncoder;
    @Mock
    private BindingResult mockResult;

    @InjectMocks
    private AuthController authController;

    @Before
    public void setUp() {
        //This method has to be called to initialize annotated fields.
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void indexRendersTheCorrectView() {
        String viewName = authController.login();
        assertEquals("auth/login", viewName);
    }

    @Test
    public void signupRendersTheCorrectView() {
        String viewName = authController.signUp(mockModel);
        assertEquals("auth/signup", viewName);
    }

    @Test
    public void signupFillTheModel() {
        ExtendedModelMap model = new ExtendedModelMap();
        authController.signUp(model);
        assertNotNull("The model is not filled", model.get("mailUser"));
    }

    @Test
    public void createRendersTheCorrectViewWhenTheUserIsCreated() {
        when(mockResult.hasErrors()).thenReturn(false);
        MailUser user = new MailUser();

        String viewName = authController.createUser(user, mockResult);
        assertEquals("home/index", viewName);
        assertEquals(true, user.isEnabled());
        assertEquals("[ROLE_USER]", user.getRoles().toString());

        verify(mockSecService, times(1)).authenticate(eq(user));
    }

    @Test
    public void createRendersTheCorrectViewWhenTheUserHasErrors() {
        when(mockResult.hasErrors()).thenReturn(true);
        String viewName = authController.createUser(new MailUser(), mockResult);
        assertEquals("auth/signup", viewName);
    }

    @Test
    public void createCallsTheServiceToSave() {
        when(mockResult.hasErrors()).thenReturn(false);
        MailUser user = new MailUser();
        user.setPassword("secret");

        authController.createUser(user, mockResult);
        verify(mockService, times(1)).createUser(eq(user));
        verify(mockEncoder, times(1)).encodePassword(eq("secret"), isNull());
    }
}
