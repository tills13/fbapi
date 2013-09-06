package com.jseb.fbapi;

import java.util.List;

import com.jseb.fbapi.base.*;

public class Link implements Idable,Commentable {
	public String id;
	public Object parent;
	public String link;
	public Profile author;
	public String message;

	public Link(String id, String link, String message, Profile author, Object parent) {
		this.id = id;
		this.parent = parent;
		this.author = author;
		this.link = link;
		this.message = message;
	}

	public List<Comment> getComments() {
		return null;
	}

	public String getFullId() {
		return this.id;
	}

	public String toString() {
		return this.author.getFullName() + ": " + this.message + " " + this.link;
	}
}