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
package com.github.carlomicieli.nerdmovies.models;

import org.bson.types.ObjectId;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Carlo Micieli
 */
public class MovieTests {

    @Test
    public void shouldBuildNewMovies() {
        Movie m = new Movie.Builder("Quentin Tarantino", "Django unchained")
                .moviedbId(140)
                .runningTime(176)
                .genre("western")
                .build();

        assertNotNull("Movie is null", m);
        assertEquals(m.getDirector(), "Quentin Tarantino");
        assertEquals(m.getTitle(), "Django unchained");
        assertEquals(m.getMoviedbId(), 140);
        assertEquals(m.getRunningTime(), 176);
        assertEquals(m.getGenre(), "western");
    }

    @Test
    public void shouldProduceStringRepresentationsForMovies() {
        Movie movie = new Movie.Builder("John Landis", "The Blues brothers").build();
        assertEquals("John Landis The Blues brothers", movie.toString());
    }

    @Test
    public void shouldCalculateSlugsForMovies() {
        Movie movie = new Movie.Builder("John Landis", "The Blues brothers").build();
        assertEquals("the-blues-brothers", movie.buildSlug());
    }

    @Test
    public void shouldAddTagsToMovies() {
        Movie m = new Movie();
        m.addTag("AAAA");
        m.addTag("BBBB");

        List<String> tags = m.getTags();
        assertNotNull(tags);
        assertEquals(2, tags.size());
        assertEquals("[AAAA, BBBB]", tags.toString());
    }

    @Test
    public void shouldAddCommentsToMovies() {
        Comment c1 = new Comment.Builder("A", "CCCC")
                .postedAt(new Date())
                .build();

        Comment c2 = new Comment.Builder("A", "BBBB")
                .postedAt(new Date())
                .build();

        Movie m = new Movie();
        m.addComment(c1);
        m.addComment(c2);

        List<Comment> cmms = m.getComments();
        assertNotNull(cmms);
        assertEquals(2, cmms.size());
    }
}
