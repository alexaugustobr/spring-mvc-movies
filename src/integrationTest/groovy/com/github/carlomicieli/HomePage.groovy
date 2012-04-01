package com.github.carlomicieli

import geb.Page

class HomePage extends Page {
	static url = "http://localhost:9090/spring-mvc-movies/"
	static at = { title == "Hello world" }
}
