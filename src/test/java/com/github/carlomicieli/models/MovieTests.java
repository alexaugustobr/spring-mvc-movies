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
    public void convertingToString() {
        ObjectId id = new ObjectId("47cc67093475061e3d95369d");
        Movie movie = new Movie();
        movie.setId(id);
        movie.setDirector("AAAA");
        movie.setTitle("BBBB");

        String expected = String.format("[%s] %s - %s", "47cc67093475061e3d95369d", "AAAA", "BBBB");
        assertEquals(expected, movie.toString());
    }

    @Test
    public void calculatingTheSlug() {
        Movie movie = new Movie();
        movie.setDirector("John Landis");
        movie.setTitle("The Blues brothers");

        movie.setSlug(movie.buildSlug());
        assertEquals("the-blues-brothers", movie.getSlug());
    }

    @Test
    public void addingTagToMovies() {
        Movie m = new Movie();
        m.addTag("AAAA");
        m.addTag("BBBB");

        List<String> tags = m.getTags();
        assertNotNull(tags);
        assertEquals(2, tags.size());
        assertEquals("[AAAA, BBBB]", tags.toString());
    }

    @Test
    public void addingCommentsToMovies() {
        Comment c1 = new Comment();
        c1.setAuthor("A");
        c1.setContent("BBBB");
        c1.setPostedAt(new Date());

        Comment c2 = new Comment();
        c2.setAuthor("A");
        c2.setContent("BBBB");
        c2.setPostedAt(new Date());

        Movie m = new Movie();
        m.addComment(c1);
        m.addComment(c2);

        List<Comment> cmms = m.getComments();
        assertNotNull(cmms);
        assertEquals(2, cmms.size());
    }
}
