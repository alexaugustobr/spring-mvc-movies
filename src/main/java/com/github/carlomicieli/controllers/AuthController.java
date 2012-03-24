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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.carlomicieli.models.MailUser;
import com.github.carlomicieli.services.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	private UserService userService;
	
	@Autowired
	public AuthController(UserService userService) {
		this.userService = userService;
	}
	

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "auth/login";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signUp(Model model) {
		model.addAttribute("user", new MailUser());
		return "auth/signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String createUser(@Valid MailUser user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "auth/signup";
		}
		
		// set the default values for the user
		user.init();
		
		userService.createUser(user);
		return "home/index";		
	}
}
