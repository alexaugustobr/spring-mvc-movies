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

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.carlomicieli.models.Movie;
import com.github.carlomicieli.models.SearchMovieForm;
import com.github.carlomicieli.models.Show;
import com.github.carlomicieli.models.ShowForm;
import com.github.carlomicieli.security.SecurityService;
import com.github.carlomicieli.services.LocationService;
import com.github.carlomicieli.services.MovieService;
import com.github.carlomicieli.services.ShowService;

/**
 * 
 * @author Carlo P. Micieli
 *
 */
@Controller
@RequestMapping("/shows")
public class ShowController {
	private ShowService showService;
	private MovieService movieService;
	
	@Autowired
	private SecurityService securityService;
	@Autowired
	private LocationService locationService;
	
	@Autowired
	public ShowController(ShowService showService, 
			MovieService movieService) {
		
		this.showService = showService;
		this.movieService = movieService;
	}
	
	// GET /shows/new
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String searchMovie(Model model) {
		model.addAttribute(new SearchMovieForm());
		return "show/new";
	}

	// POST /shows/new
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String findMovies(@Valid SearchMovieForm form, 
			BindingResult bindingResults,
			Model model) {
		
		if (bindingResults.hasErrors()) {
			return "show/new"; 
		}
		
		final List<Movie> movies = 
			movieService.findMovies(form.getSearchCriteria());
		
		if (movies.size()==1) {
			Movie m = movies.get(0);
			return String.format("redirect:/shows/%s/create", m.getSlug());
		}
		
		form.setResults(movies);
		model.addAttribute(form);
		return "show/new";
	}

	// GET /shows/{movieSlug}/create
	
	@RequestMapping(value = "/shows/{movieSlug}/create", method = RequestMethod.GET)
	public String createShow(@PathVariable String movieSlug, Model model) {
		final ShowForm sf = new ShowForm();
		sf.setMovie(movieService.findBySlug(movieSlug));
		model.addAttribute(sf);
		return "/show/create";
	}
	
	// POST /shows/{movie-slug}/create
	
	@RequestMapping(value = "/shows/create", method = RequestMethod.POST)
	public String save(@Valid ShowForm showForm, 
			BindingResult bindingResults, 
			Model model) {
		
		if (bindingResults.hasErrors()) {
			model.addAttribute(showForm);
			return "show/create";
		}
		
		double[] location = null;
		try {
			location = locationService.findLocation(showForm.getGeocodingAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		final Show show = new Show();
		show.setHostedBy(securityService.getCurrentUser().getUsername());
		show.setMovie(showForm.getMovie());
		show.setDescription(showForm.getDescription());
		show.setDate(showForm.getDate());
		show.setAddress(showForm.getCompleteAddress());
		show.setLocation(location[0], location[1]);
		
		showService.create(show);
		return "show/view";
	}

	// GET /shows/new
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("shows", showService.getAllShows());
		return "show/list";
	}


}
