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

import org.hibernate.validator.constraints.NotEmpty;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.Date;

/**
 * It represents a user's comment.
 *
 * @author Carlo Micieli
 */
public class Comment {

    @NotEmpty
    private String author;
    @NotEmpty
    private String content;
    private Date postedAt;

    public Comment() {
    }

    private Comment(Builder b) {
        this.author = b.author;
        this.content = b.content;
        this.postedAt = b.postedAt;
    }

    public static class Builder {
        private final String author;
        private final String content;
        private Date postedAt = null;

        public Builder(String author, String content) {
            this.author = author;
            this.content = content;
        }

        public Builder postedAt(Date a) {
            this.postedAt = a;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Date postedAt) {
        this.postedAt = postedAt;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(getAuthor())
                .append(" - ")
                .append(getContent())
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Comment)) return false;

        Comment other = (Comment) obj;
        return new EqualsBuilder()
                .append(author, other.author)
                .append(content, other.content)
                .append(postedAt, other.postedAt)
                .isEquals();
    }
}
