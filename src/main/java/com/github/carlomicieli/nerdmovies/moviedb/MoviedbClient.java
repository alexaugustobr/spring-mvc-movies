/*
 * Copyright 2013 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.github.carlomicieli.nerdmovies.moviedb;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static java.net.URLEncoder.encode;

/**
 * @author Carlo Micieli
 */
public class MoviedbClient {
    private static final String MUSICDB_API_ENDPOINT = "http://api.themoviedb.org/3";
    private String apikey;

    @Autowired
    private RestTemplate restTemplate;

    public MoviedbClient(String apikey) {
        this.apikey = apikey;
    }

    protected RestTemplate rest() {
        return restTemplate;
    }

    private String movieUrl() {
        return MUSICDB_API_ENDPOINT + "/movie/{id}?api_key={apikey}";
    }

    public String findMovieByID(int movieId) {
        if (apikey == null) {
            throw new RuntimeException("Invalid api key");
        }

        MovieResult response = restTemplate.getForObject(movieUrl(),
                MovieResult.class, movieId, apikey);

//        Object id = response.get("id");
//        String imdbId = (String) response.get("imdb_id");
//
//        String title = (String) response.get("original_title");
//        List<?> genres = (List<?>) response.get("genres");

        return response.toString();

    }
}
