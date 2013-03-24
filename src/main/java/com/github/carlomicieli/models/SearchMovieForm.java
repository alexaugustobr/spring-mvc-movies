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

import java.util.List;

/**
 * It represents a movie search criteria form.
 *
 * @author Carlo Micieli
 */
public class SearchMovieForm {
    @NotEmpty(message = "searchCriteria.required")
    private String searchCriteria;
    private List<Movie> movies;

    public SearchMovieForm() {
    }

    public SearchMovieForm(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    /**
     * Return true if movies are found, false otherwise.
     *
     * @return true if movies are found, false otherwise.
     */
    public boolean isFound() {
        return movies != null && movies.size() > 0;
    }

    /**
     * Return the movies search criteria.
     *
     * @return the movies search criteria.
     */
    public String getSearchCriteria() {
        return searchCriteria;
    }

    /**
     * Set the movies search criteria.
     *
     * @param searchCriteria the movies search criteria.
     */
    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    /**
     * Set the result list.
     *
     * @param movies the list of movies found.
     */
    public void setResults(List<Movie> movies) {
        this.movies = movies;
    }

    /**
     * Return the result list.
     *
     * @return the list of movies found.
     */
    public List<Movie> getResults() {
        return movies;
    }
}
