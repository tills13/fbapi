package com.jseb.fbapi;

import java.util.List;

import com.jseb.fbapi.base.*;

public class Comment implements Idable,Likeable { 
	public String id;
	public Commentable parent;
	public Profile author;
	public String message;	
	public List<Profile> likes;

	public Comment(String id, String message, Profile author, Commentable parent) {
		this.id = id;
		this.parent = parent;
		this.author = author;
		this.parent = parent;
		this.message = message;
		this.likes = FacebookAPI.getLikes(this);
	}

	public void like() {
		FacebookAPI.like(this);
	}

	public void unlike() {
		FacebookAPI.unlike(this);
	}

	public void editMessage(String newMessage) {
		this.message = newMessage;
	}

	public int getNumLikes() {
		return this.likes.size();
	}

	public List<Profile> getLikes() {
		return (this.likes == null) ? FacebookAPI.getLikes(this) : this.likes;
	}

	public String getFullId() {
		return this.parent.getFullId() + "_" + this.id;
	}

	public Commentable getParent() {
		return this.parent;
	}

	public String toString() {
		return this.author.getFullName() + ": " + this.message;
	}
}