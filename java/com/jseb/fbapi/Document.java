package com.jseb.fbapi;

import java.util.List;

import com.jseb.fbapi.base.*;

public class Document implements Idable,Likeable,Commentable {
	private String id;
	private Idable parent;
	private String subject; 
	private String content;
	private Profile author;
	private boolean canEdit;
	private List<Profile> likes;
	private List<Comment> comments;

	public Document(String subject, String id, String content, boolean canEdit, Profile author, Idable parent) {
		this.id = id;
		this.subject = subject;
		this.content = content;
		this.author = author;
		this.canEdit = canEdit;
	}

	public String getSubject() {
		return this.subject;
	}

	public String getContent() {
		return this.content;
	}

	public Profile getAuthor() {
		return this.author;
	}

	public void like() {
		FacebookAPI.like(this);
	}

	public void unlike() {
		FacebookAPI.unlike(this);
	}

	public void comment(String comment) {
		
	}

	public List<Comment> getComments() {
		return (this.comments == null) ? FacebookAPI.getComments(this) : this.comments;
	}

	public int getNumLikes() {
		return this.likes.size();
	}

	public List<Profile> getLikes() {
		return (this.likes == null) ? FacebookAPI.getLikes(this) : this.likes;
	}

	public String getFullId() {
		return id;
	}

	public Idable getParent() {
		return this.parent;
	}

	public String toString() {
		return this.subject + " by " + this.author;
	}
}