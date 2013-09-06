package com.jseb.fbapi;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.jseb.fbapi.base.*;

public class Post implements Idable,Commentable,Likeable {
	private String id;
	private Idable parent;
	private Profile author; 
	private String message; 
	private List<Comment> comments;
	private List<Profile> likes;
	private String createdTime;

	public Post(String id) {
		this.id = id;
	}

	public Post(String id, String message, Profile author, Idable parent) {
		this.id = id;
		this.parent = parent;
		this.message = message;
		this.author = author;
		this.comments = getComments();
	}

	public String moToEnglish() {
		return "idiot";
	}

	public List<Comment> getComments() {
		return this.comments == null ? this.comments = FacebookAPI.getComments(this) : this.comments;
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

	public Idable getParent() {
		return this.parent;
	}

	public String toString() {
		return this.author + " " + this.message;
	}
}