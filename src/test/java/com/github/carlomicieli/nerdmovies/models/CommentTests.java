/*
 * Copyright 2013 the original author or authors.
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

import static junit.framework.Assert.*;
import org.junit.Test;

import java.util.Date;

/**
 * @author Carlo Micieli
 */
public class CommentTests {
    @Test
    public void shouldBuildNewComments() {
        Date now = new Date();
        Comment c = new Comment.Builder("John Doe", "My comment")
                .postedAt(now)
                .build();

        assertEquals(c.getAuthor(), "John Doe");
        assertEquals(c.getContent(), "My comment");
        assertEquals(c.getPostedAt(), now);
    }

    @Test
    public void shouldReturnStringRepresentationsForComments() {
        Comment c = new Comment.Builder("John Doe", "My comment")
                .build();
        assertEquals(c.toString(), "John Doe - My comment");
    }

    @Test
    public void shouldCheckWhetherTwoCommentsAreEquals() {
        Date now = new Date();
        Comment x = new Comment.Builder("John Doe", "My comment")
                .postedAt(now)
                .build();
        Comment y = new Comment.Builder("John Doe", "My comment")
                .postedAt(now)
                .build();
        assertTrue("The comments are different", x.equals(y));
    }

    @Test
    public void shouldCheckWhetherTwoCommentsAreDifferent() {
        Date now = new Date();
        Comment x = new Comment.Builder("John Doe", "My comment")
                .postedAt(now)
                .build();
        Comment y = new Comment.Builder("Jane Doe", "My comment")
                .postedAt(now)
                .build();
        assertFalse("The comments are equals", x.equals(y));

        Comment t = new Comment.Builder("John Doe", "My other comment")
                .postedAt(now)
                .build();
        assertFalse("The comments are equals", x.equals(t));
    }

}
