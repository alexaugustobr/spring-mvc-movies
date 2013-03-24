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
package com.github.carlomicieli.controllers;

import com.github.carlomicieli.models.Movie;
import com.github.carlomicieli.models.SearchMovieForm;
import com.github.carlomicieli.models.Show;
import com.github.carlomicieli.models.ShowForm;
import com.github.carlomicieli.security.MailUserDetails;
import com.github.carlomicieli.security.SecurityService;
import com.github.carlomicieli.services.LocationService;
import com.github.carlomicieli.services.MovieService;
import com.github.carlomicieli.services.ShowService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestClientException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * @author Carlo Micieli
 */
@RunWith(MockitoJUnitRunner.class)
public class ShowControllerTests {
    @Mock
    private BindingResult mockBinding;
    @Mock
    private Model mockModel;
    @Mock
    private SecurityService securityService;
    @Mock
    private LocationService locationService;
    @Mock
    private MovieService movieService;
    @Mock
    private ShowService showService;
    @InjectMocks
    private ShowController showController;

    @Before
    public void setUp() {
        //This method has to be called to initialize annotated fields.
        MockitoAnnotations.initMocks(this);
    }

    // GET /shows

    @Test
    public void shouldListTheShows() {
        ExtendedModelMap model = new ExtendedModelMap();
        when(showService.getAllShows()).thenReturn(Arrays.asList(new Show(), new Show()));

        String viewName = showController.list(model);

        assertEquals("show/list", viewName);
        verify(showService, times(1)).getAllShows();
        assertNotNull(model.get("shows"));
    }

    // GET /shows/new

    @Test
    public void shouldInitializeNewShowView() {
        String viewName = showController.searchMovie(mockModel);
        assertEquals("show/new", viewName);
        verify(mockModel, times(1)).addAttribute(isA(SearchMovieForm.class));
    }

    // POST /shows/new

    @Test
    public void shouldProcessOnlyValidForms() {
        when(mockBinding.hasErrors()).thenReturn(true);
        SearchMovieForm form = new SearchMovieForm("");

        String viewName = showController.findMovies(form, mockBinding, mockModel);

        assertEquals("show/new", viewName);
        verify(movieService, times(0)).findMovies(isA(String.class));
    }

    @Test
    public void shoudlRedirectIfNoMoviesAreFound() {
        SearchMovieForm form = new SearchMovieForm("animal house");
        when(movieService.findMovies(eq("animal house"))).thenReturn(new ArrayList<Movie>());

        String viewName = showController.findMovies(form, mockBinding, mockModel);

        assertEquals("show/new", viewName);
        assertEquals(0, form.getResults().size());
        assertEquals(false, form.isFound());
    }

    @Test
    public void shouldShowSelectionIfMoreMoviesAreFound() {
        List<Movie> movies = Arrays.asList(new Movie(), new Movie());
        when(movieService.findMovies(eq("animal house"))).thenReturn(movies);
        when(mockBinding.hasErrors()).thenReturn(false);
        SearchMovieForm form = new SearchMovieForm("animal house");

        String viewName = showController.findMovies(form, mockBinding, mockModel);

        assertEquals("show/new", viewName);
        assertEquals(2, form.getResults().size());
        verify(mockModel, times(1)).addAttribute(eq(form));
    }

    @Test
    public void shouldRedirectIfOneMovieIsFound() {
        Movie m = new Movie();
        m.setSlug("animal-house");

        when(movieService.findMovies(eq("animal house"))).thenReturn(Arrays.asList(m));
        when(mockBinding.hasErrors()).thenReturn(false);

        SearchMovieForm form = new SearchMovieForm("animal house");

        String viewName = showController.findMovies(form, mockBinding, mockModel);

        verify(movieService, times(1)).findMovies(eq("animal house"));
        assertEquals("redirect:/shows/animal-house/create", viewName);
    }

    // GET /shows/{movie-slug}/create

    @Test
    public void shouldInitializeCreateShowForm() {
        String viewName = showController.createShow("", mockModel);
        assertEquals("/show/create", viewName);
        verify(mockModel, times(1)).addAttribute(isA(ShowForm.class));
    }

    @Test
    public void shouldSeachAndAddToModelTheMovie() {
        ExtendedModelMap model = new ExtendedModelMap();
        Movie m = new Movie();
        when(movieService.findBySlug(eq("animal-house"))).thenReturn(m);

        showController.createShow("animal-house", model);

        verify(movieService, times(1)).findBySlug(eq("animal-house"));
        ShowForm sf = (ShowForm) model.get("showForm");
        assertNotNull(sf);
        assertNotNull(sf.getMovie());
    }

    // POST /shows/{movie-slug}/create

    @Test
    public void shouldSaveShows() throws RestClientException, UnsupportedEncodingException {
        ShowForm showForm = new ShowForm();
        MailUserDetails user = new MailUserDetails("joey", "secret");
        when(securityService.getCurrentUser()).thenReturn(user);
        when(mockBinding.hasErrors()).thenReturn(false);
        when(locationService.findLocation(isA(String.class))).thenReturn(new double[]{90.0, 110.0});

        String viewName = showController.save(showForm, mockBinding, mockModel);

        assertEquals("show/view", viewName);
        verify(showService, times(1)).create(isA(Show.class));
    }

    @Test
    public void shouldFillShowWithTheFormValues() throws RestClientException, UnsupportedEncodingException {
        Movie m = new Movie();
        m.setDirector("John Landis");
        m.setTitle("The blues brothers");

        ShowForm sf = new ShowForm();
        sf.setVenue("Lumiere Theater");
        sf.setAddress("1600 Amphitheatre Pkwy");
        sf.setPostalCode("94043");
        sf.setCity("Mountain View");
        sf.setMovie(m);
        sf.setDescription("My test show");
        sf.setDate(new Date());

        MailUserDetails user = new MailUserDetails("joey", "secret");
        when(securityService.getCurrentUser()).thenReturn(user);
        when(locationService.findLocation(eq(sf.getGeocodingAddress()))).thenReturn(new double[]{90.0, 110.0});

        ArgumentCaptor<Show> argument = ArgumentCaptor.forClass(Show.class);

        showController.save(sf, mockBinding, mockModel);

        verify(showService, times(1)).create(argument.capture());
        assertEquals(sf.getDescription(), argument.getValue().getDescription());
        assertEquals(sf.getDate(), argument.getValue().getDate());
        assertEquals(m.getTitle(), argument.getValue().getMovie().getTitle());
        assertEquals("joey", argument.getValue().getHostedBy());
        assertEquals("Lumiere Theater, 1600 Amphitheatre Pkwy, 94043, Mountain View", argument.getValue().getAddress());
    }

    @Test
    public void shouldRedirectToCreateFormAfterErrors() {
        when(mockBinding.hasErrors()).thenReturn(true);
        ShowForm showForm = new ShowForm();

        String viewName = showController.save(showForm, mockBinding, mockModel);

        assertEquals("show/create", viewName);
        verify(mockModel, times(1)).addAttribute(eq(showForm));
    }

}
