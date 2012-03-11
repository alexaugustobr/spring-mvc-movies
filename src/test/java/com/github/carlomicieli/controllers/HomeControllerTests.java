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

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HomeControllerTests {
	private HomeController homeController;
	
	@Before
	public void init() {
		homeController = new HomeController();
	}
	
	@Test
	public void actionIndexProduceTheCorrectViewName() {
		String viewName = homeController.index();
		assertEquals("home/index", viewName);
	}
	
	@Test
	public void actionAboutProduceTheCorrectViewName() {
		String viewName = homeController.about();
		assertEquals("home/about", viewName);
	}
}
