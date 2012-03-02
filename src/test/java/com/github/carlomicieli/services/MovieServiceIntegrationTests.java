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

import java.util.ArrayList;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.carlomicieli.AbstractIntegrationTests;
import com.github.carlomicieli.models.Movie;
import com.github.carlomicieli.services.MovieService;

public class MovieServiceIntegrationTests extends AbstractIntegrationTests {
	private @Autowired MovieService movieService;		
	
	@Before
	public void setup() {
		Movie m1 = new Movie();
		m1.setDirector("AAAA");
		m1.setTitle("BBBB");
		
		Collection<Movie> movies = new ArrayList<Movie>();
		movies.add(m1);
		
		testHelper.initMovies(movies);
	}
	
	@After
	public void cleanup() {
		testHelper.cleanupMovies();
	}
	
	@Test
	public void getAllMoviesReturnsAllTheItems() {
		Collection<Movie> movies = movieService.getAllMovies(1, 0);
		assertNotNull(movies);
		assertEquals(1, movies.size());
	}
}
