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
package com.github.carlomicieli.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.validation.BindingResult;

import com.github.carlomicieli.models.Movie;
import com.github.carlomicieli.services.MovieService;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MovieControllerTests {

	@Mock private Model mockModel;
	@Mock private MovieService mockService;
	@Mock private BindingResult mockResult;
	
	@InjectMocks private MovieController movieController;
	
	@Before
	public void setUp() {
		//This method has to be called to initialize annotated fields.
		MockitoAnnotations.initMocks(this);
	}
	
	// GET /movies
	
	@Test
	public void actionListProduceTheCorrectViewName() {
		String viewName = movieController.list(0, 0, mockModel);
		assertEquals("movie/list", viewName);
	}
	
	@Test
	public void listPaginateTheResults() {
		List<Movie> movies = new ArrayList<Movie>();
		ExtendedModelMap model = new ExtendedModelMap();
		when(mockService.getAllMovies(eq(0), eq(10))).thenReturn(movies);
		
		movieController.list(0, 10, model);
		
		verify(mockService).getAllMovies(0, 10);
		assertTrue("Model doesn't contain the movies",
				model.containsAttribute("movies"));
		assertSame(movies, model.get("movies"));
	}
		
	// GET /movies/new
	
	@Test
	public void actionNewMovieProduceTheCorrectViewName() {
		String viewName = movieController.newMovie(mockModel);
		assertEquals("movie/new", viewName);
	}
	
	@Test
	public void actionNewInitializeTheModel() {
		ExtendedModelMap model = new ExtendedModelMap();
		
		movieController.newMovie(model);
		
		assertTrue("Model doesn't contain the movie",
				model.containsAttribute("movie"));
		assertTrue("The model is not a movie", model.get("movie") instanceof Movie);
	}
	
	// POST /movies

	@Test
	public void actionSaveCreateANewMovie() {
		Movie movie = new Movie();
		when(mockResult.hasErrors()).thenReturn(false);
		
		String viewName = movieController.save(movie, mockResult, mockModel);
	
		assertEquals("redirect:movies", viewName);
		verify(mockService).save(eq(movie));
	}
	
	@Test
	public void actionSaveRedirectAfterValidationError() {
		Movie movie = new Movie();
		ExtendedModelMap model = new ExtendedModelMap();
		when(mockResult.hasErrors()).thenReturn(true);
		
		String viewName = movieController.save(movie, mockResult, model);
		assertEquals("movie/new", viewName);
		assertTrue("The model doesn't contain the movie that failed the validation",
				model.containsAttribute("movie"));
		assertTrue("The model doesn't contain a movie", 
				model.get("movie") instanceof Movie);
		
		verify(mockService, times(0)).save(eq(movie));
	}
	
	// DELETE /movies/{movieId}
	
	@Test
	public void actionDeleteRedirectCorrectly() {
		String viewName = movieController.delete(new Movie());
		assertEquals("redirect:../movies", viewName);
	}
	
	@Test
	public void actionDeleteRemoveTheMovie() {
		Movie movie = new Movie();		
		movieController.delete(movie);
		verify(mockService, times(1)).delete(eq(movie));
	}
	
	// GET /movies/{movieId}/edit
	
	@Test
	public void actionEditProduceTheCorrectViewName() {
		String viewName = movieController.edit("47cc67093475061e3d95369d", mockModel);
		assertEquals("movie/edit", viewName);
	}
	
	@Test
	public void actionEditFillTheModel() {
		ExtendedModelMap model = new ExtendedModelMap();
		ObjectId id = new ObjectId("47cc67093475061e3d95369d");
		Movie movie = new Movie();
		when(mockService.findById(id)).thenReturn(movie);
		
		movieController.edit(id.toString(), model);
		
		assertTrue("The model doesn't contain the movie that failed the validation",
				model.containsAttribute("movie"));
		assertTrue("The model doesn't contain a movie", 
				model.get("movie") instanceof Movie);
		verify(mockService, times(1)).findById(eq(id));
	}
	
	// PUT /movies/{movieId}/edit
	
	@Test
	public void actionUpdateSaveTheMovie() {
		when(mockResult.hasErrors()).thenReturn(false);
		Movie movie = new Movie();
		
		String viewName = movieController.update(movie, mockResult);
		assertEquals("redirect:../movies", viewName);
		verify(mockService).save(eq(movie));
	}
	
	@Test
	public void actionUpdateDontSaveTheMovieAfterFailedValidation() {
		when(mockResult.hasErrors()).thenReturn(true);
		Movie movie = new Movie();
		
		String viewName = movieController.update(movie, mockResult);
		assertEquals("movie/edit", viewName);
		verify(mockService, times(0)).save(eq(movie));
	}
}
