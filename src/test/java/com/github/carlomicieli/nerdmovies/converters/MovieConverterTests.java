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
package com.github.carlomicieli.nerdmovies.converters;

import com.github.carlomicieli.nerdmovies.models.Movie;
import com.github.carlomicieli.nerdmovies.services.MovieService;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.when;

/**
 * @author Carlo Micieli
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieConverterTests {

    @Mock
    private MovieService mockService;

    @InjectMocks
    private MovieConverter movieConverter;

    @Before
    public void setUp() {
        //This method has to be called to initialize annotated fields.
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void convertStringToMovie() {
        when(mockService.findById(isA(ObjectId.class))).thenReturn(new Movie());

        Movie m = movieConverter.convert("47cc67093475061e3d95369d");
        assertNotNull("Movie not found", m);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidValueForMovieIdThrowAnException() {
        movieConverter.convert(""); //Not a valid ObjectId!
    }
}
