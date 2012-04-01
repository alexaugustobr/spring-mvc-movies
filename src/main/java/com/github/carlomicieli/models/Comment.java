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

import org.hibernate.validator.constraints.NotEmpty;

/**
 * It represents a user's comment.
 * 
 * @author Carlo P. Micieli
 *
 */
public class Comment {
	
	public Comment() {
		
	}
	
	@NotEmpty
	private String author;
	@NotEmpty
	private String content;
	private Date postedAt;
	
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
}
