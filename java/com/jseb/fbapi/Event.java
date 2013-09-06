package com.jseb.fbapi;

import java.util.*;

import com.jseb.fbapi.json.*;
import com.jseb.fbapi.base.*;

public class Event implements Idable { 
	public String id;
	public Idable parent;
	public String name;
	public String time_zone;
	public String start_time;
	public String end_time;
	public String location;
	public List<Profile> invited;
	public List<Profile> attending;

	public Event(String id, String name, String location, Idable parent) {
		this.id = id;
		this.parent = parent;
		this.name = name;
		this.location = location;
	}

	public void setTimezone(String time_zone) {
		this.time_zone = time_zone;
	}

	public void setStartAndEndTime(String start_time, String end_time) {
		this.start_time = start_time;
		this.end_time = end_time;
	}

	public List<Profile> getAttending() {
		List<Profile> list = new ArrayList<Profile>();
		JSONObject response = FacebookAPI.getServerResponse(getFullId() + "/attending");

		JSONArray attending = response.getJSONArray("data");

		for (int i = 0; i < attending.length(); i++) {
			JSONObject attendee = (JSONObject)attending.get(i);

			String name = attendee.getString("name");
			String id = attendee.getString("id");

			list.add(new Profile(name, id));
		}

		return list;
	}

	public Map<Profile, String> getInvited() {
		Map<Profile, String> map = new HashMap<Profile, String>();
		JSONObject response = FacebookAPI.getServerResponse(getFullId() + "/invited");

		JSONArray invited = response.getJSONArray("data");

		for (int i = 0; i < invited.length(); i++) {
			JSONObject minvite = (JSONObject)invited.get(i);

			String name = minvite.getString("name");
			String id = minvite.getString("id");
			String rsvp_status = minvite.getString("rsvp_status");

			map.put(new Profile(name, id), rsvp_status);
		}

		return map;
	}

	public String getFullId() {
		return FacebookAPI.base_url + this.id; 
	}

	public Idable getParent() {
		return this.parent;
	}
}