public class Location {
	public String id;
	public Object parent;
	public String latitude; 
	public String longitude;
	public String name;
	public String street;
	public String zip;

	public Location(String id, String latitude, String longitude, String name, Object parent) {
		this.id = id;
		this.parent = parent;
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
	}

	public String getFullId() {
		return this.id;
	}
}