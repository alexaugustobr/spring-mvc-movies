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
import com.github.carlomicieli.models.MailUser;
import com.mongodb.DBCollection;

@Component("testHelper")
public class TestHelper {
	private MongoTemplate mongoTemplate;
	
	@Autowired
	public TestHelper(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	public void fillMovies(Collection<Movie> movies) {
		mongoTemplate.insert(movies, Movie.class);
	}
	
	public void initMovies() {
		mongoTemplate.createCollection(Movie.class);
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
	
	public void initUsers() {
		DBCollection userColl = mongoTemplate.getCollection("users");
		
		
		mongoTemplate.dropCollection(MailUser.class);
		@SuppressWarnings("unused")
		DBCollection coll = mongoTemplate.createCollection(MailUser.class);
	}
	
	public void fillUsers(Collection<MailUser> users) {
		mongoTemplate.insert(users, MailUser.class);
	}
	
	public void cleanupUsers() {
		mongoTemplate.dropCollection(MailUser.class);
	}
	
	public MailUser insertUser(String email, String password, String role) {
		MailUser u = new MailUser();
		u.setEmailAddress(email);
		u.setPassword(password);
		u.addRole(role);
		mongoTemplate.insert(u);
		return u;
	}
}
