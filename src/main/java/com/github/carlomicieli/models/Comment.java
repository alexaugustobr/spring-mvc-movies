package com.github.carlomicieli.models;

import java.util.Date;

public class Comment {
	
	public Comment() {
		
	}
	
	private String author;
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
