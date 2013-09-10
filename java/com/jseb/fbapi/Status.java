package com.jseb.fbapi;

import java.util.List;

import com.jseb.fbapi.base.*;

public class Status implements Idable,Commentable,Likeable {
	private String id;	
	private Profile author; 
	private String message;
	private String link; 
	private List<Comment> comments;
	private List<Profile> likes;
	private String createdTime;
	private Idable parent;

	public Status(String id, String message, Profile author, Idable parent) {
		this.id = id;
		this.parent = parent;
		this.author = author;
		this.message = message;
		this.comments = FacebookAPI.getComments(this);
	}

	public void comment(String message) {

	}

	public List<Comment> getComments() {
		return this.comments == null ? FacebookAPI.getComments(this) : this.comments;
	}

	public void like() {
		FacebookAPI.like(this);
	}

	public void unlike() {
		FacebookAPI.unlike(this);
	}

	public int getNumLikes() {
		return this.likes.size();
	}

	public List<Profile> getLikes() {
		return (this.likes == null) ? FacebookAPI.getLikes(this) : this.likes;
	}

	public String getFullId() {
		return this.id;
	}

	public Idable getParent() {
		return this.parent;
	}

	public String toString() {
		return this.author + ": " + this.message;
	}
}