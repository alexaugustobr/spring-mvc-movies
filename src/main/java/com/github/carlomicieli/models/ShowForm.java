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


import org.hibernate.validator.constraints.NotEmpty;


/**
 * The POJO for the new show creation view.
 * 
 * @author Carlo P. Micieli
 *
 */
public class ShowForm {
	@NotEmpty(message = "searchCriteria.required")
	private String searchCriteria;
	
	private boolean isMovieFound;
	private Movie movie;
	
	@NotEmpty(groups = AddressGroup.class, message = "show.venue.required")
	private String venue;
	@NotEmpty(groups = AddressGroup.class, message = "show.address.required")
	private String address;
	@NotEmpty(groups = AddressGroup.class, message = "show.postalCode.required")
	private String postalCode;
	@NotEmpty(groups = AddressGroup.class, message = "show.city.required")
	private String city;
	
	public String getSearchCriteria() {
		return searchCriteria;
	}
	
	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
	
	public boolean isMovieFound() {
		return isMovieFound;
	}
	
	public void setMovieFound(boolean isMovieFound) {
		this.isMovieFound = isMovieFound;
	}
	
	public Movie getMovie() {
		return movie;
	}
	
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
	public String getVenue() {
		return venue;
	}
	
	public void setVenue(String venue) {
		this.venue = venue;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getPostalCode() {
		return postalCode;
	}
	
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String buildCompleteAddress() {
		StringBuilder sb = new StringBuilder()
			.append(getAddress() + ", ")
			.append(getPostalCode() + ", ")
			.append(getCity());
		
		return sb.toString();
	}
}
