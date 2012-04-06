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

import static org.junit.Assert.*;

/**
 * 
 * @author Carlo P. Micieli
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
	ValidatorConfiguration.class})
public class MovieValidationTests {
	private @Autowired LocalValidatorFactoryBean validatorFactory;
	private Validator validator;
	
	@Before
	public void initValidator() {
		validatorFactory.setProviderClass(Movie.class);
		validatorFactory.setTraversableResolver(new DefaultTraversableResolver());
		validator = validatorFactory.getValidator();
	}
	
	@Test
	public void shouldValidateCorrectMovie() {
		Movie movie = new Movie();
		movie.setDirector("AAAA");
		movie.setTitle("BBBB");
		movie.setYear(2012);
		movie.setRunningTime(100);
		Set<ConstraintViolation<Movie>> violations = validator.validate(movie);
		assertEquals(0, violations.size());
	}
	
	@Test
	public void shouldValidateEmptyMovie() {
		Movie movie = new Movie();
		movie.setDirector("");
		movie.setTitle("");
		movie.setYear(0);
		movie.setRunningTime(0);
		Set<ConstraintViolation<Movie>> violations = validator.validate(movie);
		assertEquals(4, violations.size());
	}
	
	@Test
	public void shouldValidateWrongMovie() {
		Movie movie = new Movie();
		Set<ConstraintViolation<Movie>> violations = validator.validate(movie);
		
		assertEquals(4, violations.size());
	}
}
