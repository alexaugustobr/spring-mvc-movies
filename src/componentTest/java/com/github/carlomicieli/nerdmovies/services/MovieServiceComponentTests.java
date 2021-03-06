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
package com.github.carlomicieli.nerdmovies.services;

import com.github.carlomicieli.nerdmovies.AbstractComponentTests;
import com.github.carlomicieli.nerdmovies.models.Movie;
import com.github.carlomicieli.nerdmovies.utility.PaginatedResult;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Carlo Micieli
 */
public class MovieServiceComponentTests extends AbstractComponentTests {
    @Autowired
    private MovieService movieService;

    @Before
    public void setup() {
        List<Movie> movies = new ArrayList<Movie>();

        Movie m1 = new Movie();
        m1.setDirector("John Landis");
        m1.setTitle("National Lampoon's Animal House");
        movies.add(m1);

        Movie m2 = new Movie();
        m2.setDirector("John Landis");
        m2.setTitle("The blues brothers");
        movies.add(m2);

        testHelper.fillMovies(movies);
    }

    @After
    public void cleanup() {
        testHelper.cleanupMovies();
    }

    @Test
    public void shouldReturnTheRecentMovies() {
        List<Movie> movies = movieService.getRecentMovies(1);
        assertNotNull(movies);
        assertEquals(1, movies.size());
    }

    @Test
    public void getAllMoviesReturnsMovies() {
        int pageNum = 1;
        int pageSize = 1;
        PaginatedResult<Movie> results = movieService.getAllMovies(pageNum, pageSize);

        List<Movie> movies = results.getData();
        assertNotNull(movies);
        assertEquals(1, movies.size());
    }

    @Test
    public void findByIdReturnNullWhenMovieNotFound() {
        Movie m = movieService.findById(new ObjectId());
        assertNull("A movie was found", m);
    }

    @Test
    public void findByIdReturnsMovie() {
        Movie newMovie = testHelper.insertMovie("John Landis", "Trading Places");
        Movie m = movieService.findById(newMovie.getId());
        assertNotNull("Movie not found", m);
    }

    @Test
    public void deletingMovie() {
        Movie newMovie = testHelper.insertMovie("John Landis", "Trading Places");

        movieService.delete(newMovie);

        Movie m = movieService.findById(newMovie.getId());
        assertNull("A movie was found", m);
    }

    @Test
    public void savingAMovie() {
        Movie newMovie = testHelper.insertMovie("John Landis", "Trading Place");
        newMovie.setTitle("Trading Places");
        movieService.save(newMovie);

        Movie m = movieService.findById(newMovie.getId());
        assertNotNull("Movie not found", m);
        assertEquals("John Landis", m.getDirector());
        assertEquals("Trading Places", m.getTitle());
        assertEquals("trading-places", m.getSlug());
    }

    @Test
    public void shouldFindMovies() {
        List<Movie> movies = movieService.findMovies("blues");
        assertEquals(1, movies.size());
        assertEquals("The blues brothers", movies.get(0).getTitle());
    }
}
