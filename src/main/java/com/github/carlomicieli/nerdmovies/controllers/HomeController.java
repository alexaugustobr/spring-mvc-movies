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

import com.github.carlomicieli.nerdmovies.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Carlo Micieli
 */
@Controller
@RequestMapping("/")
public class HomeController {

    private MovieService movieService;

    @Autowired
    public HomeController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("movies", movieService.getRecentMovies(10));
        return "home/index";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about() {
        return "home/about";
    }

    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public String defaultPage() {
        return "home/index";
    }

}
