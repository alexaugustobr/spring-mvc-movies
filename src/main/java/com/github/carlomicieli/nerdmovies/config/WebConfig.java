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
package com.github.carlomicieli.nerdmovies.config;

import com.github.carlomicieli.nerdmovies.converters.MovieConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The configuration class for the Spring MVC application.
 *
 * @author Carlo Micieli
 */
@Configuration
@EnableWebMvc
@Import(ApplicationConfig.class)
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private ApplicationConfig applicationConfig;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        MovieConverter movieConverter = new MovieConverter(applicationConfig.movieService());
        registry.addConverter(movieConverter);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //Handles HTTP GET requests for resources by efficiently serving
        //up static resources in the ${webappRoot}/resources directory
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // add the spring security handler for thymeleaf
        registry.addInterceptor(new ImplicitObjectsInterceptor());
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);

        MappingJacksonHttpMessageConverter jacksonConverter =
                new MappingJacksonHttpMessageConverter();
        jacksonConverter.setSupportedMediaTypes(mediaTypes);

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(jacksonConverter);

        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }

    // bean for files upload.
    // commons-fileupload is required in the classpath
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(1024 * 512); // 512Kb
        return multipartResolver;
    }
}