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
package com.github.carlomicieli.models;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.github.carlomicieli.utility.Slug;

/**
 * It represents a movie.
 */
@Document(collection = "movies")
public class Movie {
	
	@Id
	private ObjectId id;
	
	@Indexed
	private String slug;
	
	@NotEmpty(message = "director.notEmpty")
	@Length(min = 0, max = 100, message = "director.length")
	private String director;
	
	@NotEmpty(message = "title.notEmpty")
	@Length(min = 0, max = 100, message = "title.length")
	private String title;
	
	private String genre;
	
	private String rating;
	
	private List<String> tags;
	private List<Comment> comments;
	
	public List<String> getTags() {
		return tags;
	}
	
	public void addTag(String tag) {
		if (tags==null) tags = new ArrayList<String>();
		tags.add(tag);
	}
	
	public List<Comment> getComments() {
		return comments;
	}
	
	public void addComment(Comment comment) {
		if (comments==null) comments = new ArrayList<Comment>();
		comments.add(comment);
	}
	
	public String getDirector() {
		return director;
	}
	
	public void setDirector(String director) {
		this.director = director;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getRating() {
		return rating;
	}
	
	public void setRating(String rating) {
		this.rating = rating;
	}
	
	public ObjectId getId() {
		return id;
	}
	
	protected void setId(ObjectId id) {
		this.id = id;
	}
	
	public String getSlug() {
		return slug;
	}
	
	protected void setSlug(String slug) {
		this.slug = slug;
	}
	
	@Override
	public String toString() {
		return String.format("[%s] %s - %s", getId(), getDirector(), getTitle());
	}

	public void makeSlug() {
		setSlug(Slug.makeSlug(getTitle()));
	}
}
