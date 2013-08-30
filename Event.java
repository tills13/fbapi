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

	public String getFullURL() {
		return FacebookAPI.base_url + "/" + this.id; 
	}
}