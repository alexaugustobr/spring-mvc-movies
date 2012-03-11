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
package com.github.carlomicieli;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.github.carlomicieli.models.Movie;

@Component("testHelper")
public class TestHelper {
	private MongoTemplate mongoTemplate;
	
	@Autowired
	public TestHelper(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	public void initMovies(Collection<Movie> movies) {
		mongoTemplate.insert(movies, Movie.class);
	}
	
	public void cleanupMovies() {
		mongoTemplate.dropCollection(Movie.class);
	}
	
	public Movie insertMovie(String director, String title) {
		Movie m = new Movie();
		m.setTitle(title);
		m.setDirector(director);
		mongoTemplate.insert(m);
		return m;
	}
}
