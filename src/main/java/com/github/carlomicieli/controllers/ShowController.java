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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.carlomicieli.models.Show;
import com.github.carlomicieli.security.SecurityService;
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
	private SecurityService securityService;
	
	@Autowired
	public ShowController(ShowService showService, SecurityService securityService) {
		this.showService = showService;
		this.securityService = securityService;
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String createShow(Model model) {
		model.addAttribute(new Show());
		return "show/new";
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String save(@Valid Show show, BindingResult bindingResults, Model model) {
		if (bindingResults.hasErrors()) {
			model.addAttribute(show);
			return "show/new";
		}
		
		show.setHostedBy(securityService.getCurrentUser().getUsername());
		
		showService.create(show);
		return "show/view";
	}
}
