package com.github.carlomicieli.nerdmovies

import geb.Page

class HomePage extends Page {
	static url = "http://localhost:9090/spring-mvc-movies/"
	static at = { title == "Spring Movies" }
}
