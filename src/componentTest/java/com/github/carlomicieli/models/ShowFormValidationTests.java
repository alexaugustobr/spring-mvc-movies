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

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.Before;
import org.junit.Test;

import com.github.carlomicieli.AbstractValidationTests;

import static org.junit.Assert.*;

/**
 * 
 * @author Carlo P. Micieli
 *
 */
public class ShowFormValidationTests extends AbstractValidationTests {
	
	@Before
	public void initValidator() {
		super.init(MailUser.class);
	}
	
	@Test
	public void shouldValidateOnlySearchCriteriaIfMovieNotFound() {
		ShowForm sf = new ShowForm();
		sf.setMovieFound(false);
		
		Set<ConstraintViolation<ShowForm>> violations = validator.validate(sf);
		assertEquals(1, violations.size());
		assertEquals("searchCriteria.required", violations.iterator().next().getMessage());
	}
	
	@Test
	public void shouldValidateAddressAfterMovieIsFound() {
		ShowForm sf = new ShowForm();
		sf.setMovieFound(true);
		sf.setSearchCriteria("animal house");
		
		sf.setVenue(""); // (1)
		sf.setAddress(""); // (2)
		sf.setCity(""); // (3)
		sf.setPostalCode(""); // (4)
		
		Set<ConstraintViolation<ShowForm>> violations = validator.validate(sf, AddressGroup.class);
		assertEquals(4, violations.size());
	}
}
