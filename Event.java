import java.util.*;

public class Event {
	public String id; 
	public String name;
	public String time_zone;
	public String start_time;
	public String end_time;
	public String location;

	public Event(String id, String name, String location) {
		this.id = id;
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
		JSONObject response = FacebookAPI.getServerResponse(getFullURL() + "/attending");

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
		JSONObject response = FacebookAPI.getServerResponse(getFullURL() + "/invited");

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

	public String getFullURL() {
		return FacebookAPI.base_url + this.id; 
	}
}