package com.jseb.fbapi;

import com.jseb.fbapi.base.*;

public class Profile implements Idable {
	public String id;
	public Object parent;
	public String name;
	public String first_name; 
	public String last_name;
	public boolean isAdmin;
	public String gender;
	public String username;

	public Profile(String name, String id) {
		this.parent = this;
		this.name = name;
		//this.first_name = name.substring(0, name.indexOf(" "));
		//this.last_name = name.substring(name.indexOf(" ") + 1, name.length());
		this.isAdmin = false;
	}

	public Profile(String id, String first_name, String last_name, String gender, String username) {
		this.parent = this;
		this.first_name = first_name;
		this.last_name = last_name;
		this.gender = gender;
		this.username = username;
	}

	public Profile(String name, String id, boolean isAdmin) {
		this(name, id);
		this.isAdmin = isAdmin;
	}

	public String getFirstName() {
		return this.first_name;
	}

	public String getLastName() {
		return this.last_name;
	}

	public String getFullName() {
		return this.first_name == null ? this.name : this.first_name + " " + this.last_name;
	}

	public String getProfileLink() {
		return "";
	}

	public String getFullId() {
		return FacebookAPI.base_url + this.id;
	}

	public Idable getParent() {
		return this;
	}

	public String toString() {
		return getFullName() + ": ";
	}
}