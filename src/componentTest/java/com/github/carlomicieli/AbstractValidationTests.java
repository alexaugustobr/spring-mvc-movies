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
package com.github.carlomicieli;

import javax.validation.Validator;

import org.hibernate.validator.engine.resolver.DefaultTraversableResolver;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Abstract test runner for validation tests.
 * 
 * @author Carlo P. Micieli
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
	ValidatorConfiguration.class})
public abstract class AbstractValidationTests {
	protected @Autowired LocalValidatorFactoryBean validatorFactory;
	protected Validator validator;
	
	@SuppressWarnings("rawtypes")
	protected void init(Class providerClass) {
		validatorFactory.setProviderClass(providerClass);
		validatorFactory.setTraversableResolver(new DefaultTraversableResolver());
		validator = validatorFactory.getValidator();
	}
}