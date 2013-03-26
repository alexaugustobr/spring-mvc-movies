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
package com.github.carlomicieli.nerdmovies.services;

import com.github.carlomicieli.nerdmovies.models.Movie;
import com.github.carlomicieli.nerdmovies.utility.PaginatedResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author Carlo Micieli
 */
@Service("movieService")
public class MongoMovieService implements MovieService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public PaginatedResult<Movie> getAllMovies(int page, int pageSize) {
        Pageable p = new PageRequest(page - 1, pageSize);
        final Query query = new Query().skip(p.getOffset()).limit(p.getPageSize());

        //counting the total number of documents in the collection
        long count = mongoTemplate.count(new Query(), Movie.class);

        List<Movie> movies = mongoTemplate.find(query, Movie.class);
        PaginatedResult<Movie> results = new PaginatedResult<Movie>(movies,
                count, page, pageSize);

        return results;
    }

    public Movie findById(ObjectId id) {
        return mongoTemplate.findById(id, Movie.class);
    }

    public void save(Movie movie) {
        mongoTemplate.save(movie);
    }

    public void delete(Movie movie) {
        mongoTemplate.remove(movie);
    }

    public Movie findBySlug(String slug) {
        return mongoTemplate.findOne(new Query(where("slug").is(slug)), Movie.class);
    }

    public List<Movie> getRecentMovies(int numOfMovies) {
        Query q = new Query().limit(numOfMovies);
        q.sort().on("savedAt", Order.DESCENDING);
        return mongoTemplate.find(q, Movie.class);
    }

    public List<Movie> findMovies(String searchCriteria) {
        Query q = query(where("title").regex(searchCriteria, "i"));
        return mongoTemplate.find(q, Movie.class);
    }
}