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
package com.github.carlomicieli.models;

import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.hibernate.validator.engine.resolver.DefaultTraversableResolver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.github.carlomicieli.ValidatorConfiguration;

/**
 * 
 * @author Carlo P. Micieli
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
	ValidatorConfiguration.class})
public class MailUserValidationTests {
	private @Autowired LocalValidatorFactoryBean validatorFactory;
	private Validator validator;
	
	@Before
	public void initValidator() {
		validatorFactory.setProviderClass(MailUser.class);
		validatorFactory.setTraversableResolver(new DefaultTraversableResolver());
		validator = validatorFactory.getValidator();
	}
	
	@Test
	public void shouldValidateCorrectUser() {
		MailUser user = new MailUser();
		user.setEmailAddress("joey@ramones.com");
		user.setPassword("secret");
		user.setDisplayName("joey");
		user.addRole("ROLE_USER");	
		Set<ConstraintViolation<MailUser>> violations = validator.validate(user);
		assertEquals(0, violations.size());
	}
	
	@Test
	public void shouldReturnValidationErrorsForNotCorrectUser() {
		MailUser user = new MailUser();
		user.setEmailAddress("joey AT ramones"); // not valid(1)
		user.setPassword(""); // not valid(2)
		user.setDisplayName(""); // not valid(3)
				
		Set<ConstraintViolation<MailUser>> violations = validator.validate(user);
		assertEquals(3, violations.size());
	}
}
