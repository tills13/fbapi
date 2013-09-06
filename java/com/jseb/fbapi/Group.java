package com.jseb.fbapi;

import java.util.List;

import com.jseb.fbapi.base.*;

public class Group implements Idable {
	public String id;
	public Object parent;
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

	public Group(String id, String name, String description, String privacy, String email, Profile owner, Object parent) {
		this.id = id;
		this.parent = 
		this.name = name;
		this.description = description;
		this. privacy = privacy;
		this.email = email;
		this.owner = owner;
	}

	public String getId() {
		return this.id;
	}

	public List<Post> getPosts() {
		return FacebookAPI.getGroupPosts(this);
	} 

	public List<Profile> getMembers() {
		return FacebookAPI.getGroupMembers(this);
	}

	public String getFullId() {
		return this.id;
	}

	public String getDescription() {
		return this.description;
	}

	public String toString() {
		return this.name + ": " + this.description;
	}
}