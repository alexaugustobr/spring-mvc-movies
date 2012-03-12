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

import java.util.List;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.carlomicieli.models.Movie;
import com.github.carlomicieli.services.MovieService;

@Controller
@RequestMapping("/movies")
public class MovieController {
	private MovieService movieService;
	
	@Autowired
	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(defaultValue = "0") int offset,
			@RequestParam(defaultValue = "100") int max,
			Model model) {
		List<Movie> movies = movieService.getAllMovies(offset, max);
		model.addAttribute("movies", movies);
		return "movie/list";
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newMovie(Model model) {
		model.addAttribute(new Movie());
		return "movie/new";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute Movie movie, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute(movie);
			return "movie/new";
		}
		
		movieService.save(movie);
		return "redirect:movies";
	}
	
	@RequestMapping(value = "/{movie}", method = RequestMethod.DELETE)
	public String delete(@PathVariable Movie movie) {
		movieService.delete(movie);
		return "redirect:../movies";
	}
	
	@RequestMapping(value = "/{movieId}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable String movieId, Model model) {
		ObjectId id = new ObjectId(movieId);
		Movie movie = movieService.findById(id);
		model.addAttribute(movie);
		return "movie/edit";
	}
	
	@RequestMapping(value = "/{movie}", method = RequestMethod.PUT)
	public String update(@Valid @ModelAttribute Movie movie, BindingResult result) {
		if (result.hasErrors()) {
			return "movie/edit";
		}
		
		movieService.save(movie);
		return "redirect:../movies";
	}

	@RequestMapping(value = "/{movieSlug}", method = RequestMethod.GET)
	public String view(@PathVariable String movieSlug, Model model) {
		Movie movie = movieService.findBySlug(movieSlug);
		model.addAttribute(movie);		
		return "movie/view";
	}
}
