/*
Copyright [2012] [Carlo P. Micieli]

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
package com.github.carlomicieli.models;

import static org.junit.Assert.assertEquals;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
	ValidatorConfiguration.class})
public class MailUserTests {
	private @Autowired LocalValidatorFactoryBean validatorFactory;
	private Validator validator;
	
	@Before
	public void initValidator() {
		validatorFactory.setProviderClass(MailUser.class);
		validatorFactory.setTraversableResolver(new DefaultTraversableResolver());
		validator = validatorFactory.getValidator();
	}
	
	@Test
	public void validatingValidUser() {
		MailUser user = new MailUser();
		user.setEmailAddress("joey@ramones.com");
		user.setPassword("secret");
		user.setDisplayName("joey");
		user.addRole("ROLE_USER");	
		Set<ConstraintViolation<MailUser>> violations = validator.validate(user);
		assertEquals(0, violations.size());
	}
	
	@Test
	public void validatingNotValidUser() {
		MailUser user = new MailUser();
		user.setEmailAddress("joey AT ramones"); // not valid(1)
		user.setPassword(""); // not valid(2)
		user.setDisplayName(""); // not valid(3)
				
		Set<ConstraintViolation<MailUser>> violations = validator.validate(user);
		assertEquals(3, violations.size());
	}
	
	@Test
	public void addingARoleToUser() {
		MailUser user = new MailUser();
		user.setEmailAddress("joey@ramones.com");
		user.setPassword("secret");
		user.addRole("ROLE_USER");	
		user.addRole("ROLE_ADMIN");	

		assertEquals(2, user.getRoles().size());
		assertEquals("[ROLE_USER, ROLE_ADMIN]", user.getRoles().toString());
	}
	
	@Test
	public void initializeANewUser() {
		MailUser user = new MailUser();
		user.init();
		
		assertEquals(true, user.isEnabled());
		assertEquals("[ROLE_USER]", user.getRoles().toString());
	}
}
