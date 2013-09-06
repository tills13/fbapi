package com.jseb.fbapi;

import com.jseb.fbapi.base.*;

public class Location implements Idable {
	public String id;
	public Idable parent;
	public String latitude; 
	public String longitude;
	public String name;
	public String street;
	public String zip;

	public Location(String id, String latitude, String longitude, String name, Idable parent) {
		this.id = id;
		this.parent = parent;
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
	}

	public String getFullId() {
		return this.id;
	}

	public Idable getParent() {
		return this.parent;
	}
}