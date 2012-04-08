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
package com.github.carlomicieli.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.github.carlomicieli.utility.Slug;

/**
 * It represents a movie.
 * 
 * @author Carlo P. Micieli
 *
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
	private String plot;

	@Range(min = 1900, max = 2012, message = "year.notValid")
	private int year;
	
	@Range(min = 1, max = 240, message = "runningTime.notValid")
	private int runningTime;
	
	private String rating;
	private byte[] poster;
	private byte[] thumb;
	
	private Date savedAt;

	private List<String> tags;
	private List<Comment> comments;
	
	/**
	 * Return the list of tags associated with this movie.
	 * @return the list of tags.
	 */
	public List<String> getTags() {
		return tags;
	}
	
	/**
	 * Add a new tag to this movie.
	 * @param tag the tag.
	 */
	public void addTag(String tag) {
		if (tags==null) tags = new ArrayList<String>();
		tags.add(tag);
	}
	
	/**
	 * Return the list of user comments.
	 * @return the list of user comments
	 */
	public List<Comment> getComments() {
		return comments;
	}
	
	/**
	 * Add a new user comment.
	 * @param comment the user comment.
	 */
	public void addComment(Comment comment) {
		if (comments==null) comments = new ArrayList<Comment>();
		comments.add(comment);
	}
	
	/**
	 * Return the timestamp when the movie was last saved.
	 * @return the saving timestamp.
	 */
	public Date getSavedAt() {
		return savedAt;
	}

	/**
	 * Set the timestamp when the movie was last saved.
	 * @param savedAt the saving timestamp.
	 */
	public void setSavedAt(Date savedAt) {
		this.savedAt = savedAt;
	}
	
	/**
	 * Return the director name.
	 * @return the director name.
	 */
	public String getDirector() {
		return director;
	}
	
	/**
	 * Set the director name.
	 * @param director the director name.
	 */
	public void setDirector(String director) {
		this.director = director;
	}
	
	/**
	 * Return the release year.
	 * @return the release year.
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Set the release year.
	 * @param year the release year.
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Return the title.
	 * @return the title.
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Set the title.
	 * @param title the title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Return the genre.
	 * @return the genre.
	 */
	public String getGenre() {
		return genre;
	}
	
	/**
	 * Set the genre.
	 * @param genre the genre.
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	/**
	 * Set the movie rating.
	 * @return the movie rating.
	 */
	public String getRating() {
		return rating;
	}
	
	/**
	 * Set the movie rating.
	 * @param rating the movie rating.
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}
	
	public ObjectId getId() {
		return id;
	}
	
	protected void setId(ObjectId id) {
		this.id = id;
	}
	
	/**
	 * Return the slug from the movie title.
	 * @return the movie slug.
	 */
	public String getSlug() {
		return slug;
	}
	
	/**
	 * Set the slug from the movie title
	 * @param slug the movie slug.
	 */
	public void setSlug(String slug) {
		this.slug = slug;
	}
	
	/**
	 * Return a summary of the movie plot.
	 * @return the plot.
	 */
	public String getPlot() {
		return plot;
	}

	/**
	 * Set a summary of the movie plot.
	 * @param plot the plot.
	 */
	public void setPlot(String plot) {
		this.plot = plot;
	}
	
	/**
	 * Return the running time in minutes.
	 * @return the running time.
	 */
	public int getRunningTime() {
		return runningTime;
	}

	/**
	 * Set the running time in minutes.
	 * @param runningTime the running time.
	 */
	public void setRunningTime(int runningTime) {
		this.runningTime = runningTime;
	}

	@Override
	public String toString() {
		return String.format("[%s] %s - %s", getId(), getDirector(), getTitle());
	}
	
	public String buildSlug() {
		return Slug.makeSlug(getTitle());
	}

	public byte[] getPoster() {
		return poster;
	}
	
	public void setPoster(byte[] poster) {
		this.poster = poster;
	}
	
	public byte[] getThumb() {
		return thumb;
	}
	
	public void setThumb(byte[] thumb) {
		this.thumb = thumb;
	}
}
