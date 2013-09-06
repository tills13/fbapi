package com.jseb.fbapi;

import java.util.List;

import com.jseb.fbapi.base.*;

public class Link implements Idable,Commentable,Likeable {
	public String id;
	public Idable parent;
	public String link;
	public Profile author;
	public String message;
	public List<Comment> comments;
	public List<Profile> likes;

	public Link(String id, String link, String message, Profile author, Idable parent) {
		this.id = id;
		this.parent = parent;
		this.author = author;
		this.link = link;
		this.message = message;
		this.comments = FacebookAPI.getComments(this);
	}

	public List<Comment> getComments() {
		return this.comments == null ? FacebookAPI.getComments(this) : this.comments;
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
		return this.author.getFullName() + ": " + this.message + " " + this.link;
	}
}