package com.jseb.fbapi;

import com.jseb.fbapi.base.*;

public class Comment implements Idable { 
	public String id;
	public Object parent;
	public Profile author;
	public String message;	
	public int likes;

	public Comment(String id, Object parent, String message, Profile author, int likes) {
		this.id = id;
		this.parent = parent;
		this.author = author;
		this.parent = parent;
		this.message = message;
		this.likes = likes;
	}

	public void incrementLikes() {
		this.likes++;
	}

	public void decrementLikes() {
		this.likes = (this.likes == 0) ? 0 : this.likes--;
	}

	public void editMessage(String newMessage) {
		this.message = newMessage;
	}

	public String getFullId() {
		if (this.parent instanceof Post) return ((Post)this.parent).getFullId() + "_" + this.id;
		else if (this.parent instanceof Link) return ((Link)this.parent).getFullId() + "_" + this.id;
		else if (this.parent instanceof Status) return ((Status)this.parent).getFullId() + "_" + this.id;
		return this.id;
	}

	public String toString() {
		return this.author.getFullName() + ": " + this.message;
	}
}