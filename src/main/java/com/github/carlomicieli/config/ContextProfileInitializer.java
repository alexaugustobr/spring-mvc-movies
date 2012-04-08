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
package com.github.carlomicieli.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.context.ConfigurableWebApplicationContext;

/**
 * Initialize the active profile for the application
 * 
 * @author Carlo P. Micieli
 *
 */
public class ContextProfileInitializer 
	implements ApplicationContextInitializer<ConfigurableWebApplicationContext> {

	/**
	 * Set the active profile to <strong>production</strong> for the web
	 * application context.
	 * @param context the web application context.
	 */
	@Override
	public void initialize(ConfigurableWebApplicationContext context) {
		ConfigurableEnvironment environment = context.getEnvironment();
		environment.setActiveProfiles("production");
	}
}