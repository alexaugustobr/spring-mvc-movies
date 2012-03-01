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
package com.github.carlomicieli.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.github.carlomicieli.converters.MovieConverter;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
	@Autowired
	private ApplicationConfig applicationConfig;
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//Handles HTTP GET requests for resources by efficiently serving 
		//up static resources in the ${webappRoot}/resources directory
    	registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
		// Resolves views selected for rendering by @Controllers to .jsp 
		// resources in the /WEB-INF/views directory
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		MovieConverter movieConverter = new MovieConverter(applicationConfig.movieService());
		registry.addConverter(movieConverter);
	}
}
