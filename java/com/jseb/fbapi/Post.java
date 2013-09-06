package com.jseb.fbapi;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.jseb.fbapi.base.*;

public class Post implements Idable,Commentable {
	private String id;
	private Object parent;
	private Profile author; 
	private String message; 
	private List<Comment> comments;
	private String createdTime;
	private int likes;

	public Post(String id) {
		this.id = id;
	}

	public Post(String id, String message, Profile author, Object parent) {
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

	public List<Profile> getLikes() {
		return new ArrayList<Profile>();
	}

	public Object getParent() {
		return this.parent;
	}

	public String getFullId() {
		if (this.parent instanceof Post) return ((Post)this.parent).getFullId() + "_" + this.id;
		else if (this.parent instanceof Link) return ((Group)this.parent).getFullId() + "_" + this.id;
		return this.id;
	}

	public String toString() {
		return this.author + " " + this.message;
	}
}