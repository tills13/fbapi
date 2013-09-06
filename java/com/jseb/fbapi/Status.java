package com.jseb.fbapi;

import java.util.List;

import com.jseb.fbapi.base.*;

public class Status implements Idable,Commentable {
	private String id;	
	private Profile author; 
	private String message;
	private String link; 
	private List<Comment> comments;
	private String createdTime;
	private int likes;
	private Object parent;

	public Status(String id, String message, Profile author, Object parent) {
		this.id = id;
		this.parent = parent;
		this.author = author;
		this.message = message;
		this.comments = FacebookAPI.getComments(this);
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	public String getFullId() {
		return this.id;
	}

	public String toString() {
		return this.author + ": " + this.message;
	}
}