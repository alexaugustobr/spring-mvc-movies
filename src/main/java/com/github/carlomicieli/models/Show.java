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

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * It represents a movie show.
 * 
 * @author Carlo P. Micieli
 *
 */
@Document(collection = "shows")
public class Show {

	@Id
	ObjectId id;
	
	@NotEmpty(message = "hostedBy.required")
	String hostedBy;
	
	@DBRef
	Movie movie;
	
	@NotNull(message = "date.required")
	Date date;
	
	Set<String> partecipants;

	double[] location;
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getHostedBy() {
		return hostedBy;
	}

	public void setLocation(double x, double y) {
		this.location = new double[] { x, y };
	}
	
	public void setHostedBy(String hostedBy) {
		this.hostedBy = hostedBy;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set<String> getPartecipants() {
		return partecipants;
	}

	public void addPartecipant(String p) {
		if (partecipants==null) partecipants = new HashSet<>();
		
		partecipants.add(p);
	}
}
