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
package com.github.carlomicieli.services;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import com.github.carlomicieli.AbstractIntegrationTests;
import com.github.carlomicieli.models.MailUser;

public class UserServiceIntegrationTests extends AbstractIntegrationTests {
	private @Autowired UserService userService;
	
	@Before
	public void setup() {
		//testHelper.initUsers();
	}
	
	@After
	public void tearDown() {
		testHelper.cleanupUsers();
	}
	
	@Test
	public void findUserById() {
		MailUser user = testHelper.insertUser("joey@ramones.com", "secret", "ROLE_USER");
		assertNotNull(user);
		
		MailUser dbUser = userService.findUserById(user.getId());
		assertNotNull(user);
		assertEquals("joey@ramones.com", dbUser.getEmailAddress());
		assertEquals("secret", dbUser.getPassword());
		assertEquals("[ROLE_USER]", dbUser.getRoles().toString());
	}
	
	@Test
	public void findUserByEmailAddress() {
		MailUser user = testHelper.insertUser("joey@ramones.com", "secret", "ROLE_USER");
		assertNotNull(user);
		
		MailUser dbUser = userService.findUserByEmail("joey@ramones.com");
		assertNotNull(user);
		assertEquals("joey@ramones.com", dbUser.getEmailAddress());
		assertEquals("secret", dbUser.getPassword());
		assertEquals("[ROLE_USER]", dbUser.getRoles().toString());
	}
	
	@Test
	public void createNewUser() {
		MailUser user = new MailUser();
		user.setEmailAddress("joey@ramones.com");
		user.setPassword("secret");
		user.addRole("ROLE_USER");
		userService.createUser(user);
		
		assertNotNull(user.getId());
	}
	
	@Test(expected = DuplicateKeyException.class)
	public void creatingTwoUsersWithTheSameEmailIsNotPossible() {
		testHelper.insertUser("joey@ramones.com", "secret", "ROLE_USER");
		
		MailUser user = new MailUser();
		user.setEmailAddress("joey@ramones.com");
		user.setPassword("password");
		user.addRole("ROLE_ADMIN");
		userService.createUser(user);
	}
}
