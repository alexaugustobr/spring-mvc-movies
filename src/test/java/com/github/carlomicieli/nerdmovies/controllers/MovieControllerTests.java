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
package com.github.carlomicieli.nerdmovies.controllers;

import com.github.carlomicieli.nerdmovies.models.Comment;
import com.github.carlomicieli.nerdmovies.models.Movie;
import com.github.carlomicieli.nerdmovies.services.MovieService;
import com.github.carlomicieli.nerdmovies.utility.PaginatedResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Carlo Micieli
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieControllerTests {

    @Mock
    private MultipartFile mockFile;
    @Mock
    private Model mockModel;
    @Mock
    private MovieService mockService;
    @Mock
    private BindingResult mockResult;

    @InjectMocks
    private MovieController movieController;

    @Before
    public void setUp() {
        //This method has to be called to initialize annotated fields.
        MockitoAnnotations.initMocks(this);
    }

    // GET /movies/{slug}

    @Test
    public void viewShowTheMovieIfFound() {
        when(mockService.findBySlug(eq("movie-slug"))).thenReturn(new Movie());

        ExtendedModelMap model = new ExtendedModelMap();
        String viewName = movieController.view("movie-slug", model);

        assertEquals("movie/view", viewName);

        assertNotNull(model.get("movie"));
        assertNotNull(model.get("newComment"));
    }

    @Test
    public void viewRenderNotFoundViewWhenMovieNotFound() {
        when(mockService.findBySlug(eq("movie-slug"))).thenReturn(null);

        String viewName = movieController.view("movie-slug", mockModel);
        assertEquals("errors/notfound", viewName);
    }

    // GET /movies/{movieId}/addcomment

    @Test
    public void actionAddCommentFillTheModel() {
        when(mockService.findBySlug(eq("movie-slug"))).thenReturn(new Movie());

        Comment comment = new Comment();
        comment.setContent("AAAA");
        ExtendedModelMap model = new ExtendedModelMap();

        String viewName = movieController.addComment("movie-slug", comment, mockResult, model);
        assertEquals("redirect:../movie-slug", viewName);

        Movie movie = (Movie) model.get("movie");

        assertNotNull(movie);
        assertNotNull(movie.getComments());
        assertEquals(1, movie.getComments().size());
        assertNotNull(model.get("newComment"));
    }

//	@Test
//	public void actionAddCommentFailedValidation() {
//		when(mockResult.hasErrors()).thenReturn(true);
//		
//		Comment comment = new Comment();
//		comment.setContent("AAAA");
//		ExtendedModelMap model = new ExtendedModelMap();
//		
//		String viewName = movieController.addComment("movie-slug", comment, mockResult, model);
//		assertEquals("redirect:../movie-slug", viewName);

//		Comment c = (Comment)model.get("newComment");
//		assertNotNull(c);
//		assertEquals("AAAA", c.getContent());
//	}

    // GET /movies

    @Test
    public void listPaginateTheResults() {
        List<Movie> movies = new ArrayList<Movie>();
        PaginatedResult<Movie> results =
                new PaginatedResult<Movie>(movies, 100, 10);
        ExtendedModelMap model = new ExtendedModelMap();
        when(mockService.getAllMovies(eq(1), eq(10))).thenReturn(results);

        String viewName = movieController.list(1, 10, model);

        assertEquals("movie/list", viewName);
        verify(mockService).getAllMovies(1, 10);
        assertTrue("Model doesn't contain the movies",
                model.containsAttribute("results"));
        assertSame(results, model.get("results"));
    }

    // GET /movies/new

    @Test
    public void newMovieProduceTheCorrectViewName() {
        String viewName = movieController.newMovie(mockModel);
        assertEquals("movie/new", viewName);
    }

    @Test
    public void newInitializeTheModel() {
        ExtendedModelMap model = new ExtendedModelMap();

        movieController.newMovie(model);

        assertTrue("Model doesn't contain the movie",
                model.containsAttribute("movie"));
        assertTrue("The model is not a movie", model.get("movie") instanceof Movie);
    }

    // POST /movies

    @Test
    public void saveCreateANewMovie() throws IOException {
        Movie movie = new Movie();
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockFile.isEmpty()).thenReturn(true);

        String viewName = movieController.save(movie, mockFile, mockResult, mockModel);

        assertEquals("redirect:movies", viewName);
        verify(mockService).save(eq(movie));
    }

    @Test
    public void saveRedirectAfterValidationError() throws IOException {
        Movie movie = new Movie();
        ExtendedModelMap model = new ExtendedModelMap();
        when(mockResult.hasErrors()).thenReturn(true);
        when(mockFile.isEmpty()).thenReturn(true);

        String viewName = movieController.save(movie, mockFile, mockResult, model);
        assertEquals("movie/new", viewName);
        assertTrue("The model doesn't contain the movie that failed the validation",
                model.containsAttribute("movie"));
        assertTrue("The model doesn't contain a movie",
                model.get("movie") instanceof Movie);

        verify(mockService, times(0)).save(eq(movie));
    }

//	@Test
//	public void savePersistThePosterImages() throws IOException {
//		Movie movie = new Movie();
//		MockMultipartFile file = new MockMultipartFile("poster.png", 
//			"poster.png",
//			MediaType.IMAGE_PNG.toString(), 
//			new byte[]{1, 2, 3});
//		when(mockResult.hasErrors()).thenReturn(false);
//		
//		movieController.save(movie, file, mockResult, mockModel);
//		
//		assertNotNull("Poster is empty", movie.getPoster());
//		assertNotNull("Thumb is empty", movie.getThumb());
//	}

    // DELETE /movies/{movieId}

    @Test
    public void actionDeleteRedirectCorrectly() {
        String viewName = movieController.delete("movie-slug");
        assertEquals("redirect:../movies", viewName);
    }

    @Test
    public void actionDeleteRemoveTheMovie() {
        Movie movie = new Movie();
        when(mockService.findBySlug(eq("movie-slug"))).thenReturn(movie);
        String movieSlug = "movie-slug";
        movieController.delete(movieSlug);
        verify(mockService, times(1)).delete(eq(movie));
    }

    // GET /movies/{movieId}/edit

    @Test
    public void actionEditProduceTheCorrectViewName() {
        String viewName = movieController.edit("movie-slug", mockModel);
        assertEquals("movie/edit", viewName);
    }

    @Test
    public void actionEditFillTheModel() {
        ExtendedModelMap model = new ExtendedModelMap();
        String slug = "movie-slug";
        Movie movie = new Movie();
        when(mockService.findBySlug(slug)).thenReturn(movie);

        movieController.edit(slug, model);

        assertTrue("The model doesn't contain the movie that failed the validation",
                model.containsAttribute("movie"));
        assertTrue("The model doesn't contain a movie",
                model.get("movie") instanceof Movie);
        verify(mockService, times(1)).findBySlug(eq(slug));
    }

    // PUT /movies/{movieId}/edit

    @Test
    public void actionUpdateSaveTheMovie() throws IOException {
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockFile.isEmpty()).thenReturn(true);
        Movie movie = new Movie();

        String viewName = movieController.update(movie, mockFile, mockResult);
        assertEquals("redirect:../../movies", viewName);
        verify(mockService).save(eq(movie));
    }

    @Test
    public void actionUpdateDontSaveTheMovieAfterFailedValidation() throws IOException {
        when(mockResult.hasErrors()).thenReturn(true);
        when(mockFile.isEmpty()).thenReturn(true);
        Movie movie = new Movie();

        String viewName = movieController.update(movie, mockFile, mockResult);
        assertEquals("movie/edit", viewName);
        verify(mockService, times(0)).save(eq(movie));
    }
}
