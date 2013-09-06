package com.jseb.fbapi;

import java.util.List;

import com.jseb.fbapi.base.*;

public class Group implements Idable,Postable {
	public String id;
	public Idable parent;
	public String name;
	public String description;
	public String link;
	public String privacy;
	public String email;
	public Profile owner;
	public List<Post> posts;
	public List<Profile> members;

	public Group(String id) {
		this.id = id;
	}

	public Group(String id, String name, String description, String privacy, String email, Profile owner, Idable parent) {
		this.id = id;
		this.parent = parent;
		this.name = name;
		this.description = description;
		this.privacy = privacy;
		this.email = email;
		this.owner = owner;
	}

	public void post(String message) {

	}

	public void post(String message, String link) {
		
	}

	public List<Post> getPosts() {
		return FacebookAPI.getGroupPosts(this);
	} 

	public List<Profile> getMembers() {
		return FacebookAPI.getGroupMembers(this);
	}

	public String getDescription() {
		return this.description;
	}

	public String getFullId() {
		return this.id;
	}

	public Idable getParent() {
		return this.parent;
	}

	public String toString() {
		return this.name + ": " + this.description;
	}
}