public class Profile {
	public String first_name; 
	public String last_name;
	public String id;
	public boolean isAdmin;
	public String gender;
	public String username;

	public Profile(String name, String id) {
		//this.name = name;
		this.first_name = name.substring(0, name.indexOf(" "));
		this.last_name = name.substring(name.indexOf(" ") + 1, name.length());
		this.id = id;
		this.isAdmin = false;
	}

	public Profile(String id, String first_name, String last_name, String gender, String username) {
		this.id = id;
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
		return this.first_name + " " + this.last_name;
	}

	public String getProfile() {
		return "";
	}

	public String toString() {
		return getFullName() + " " + this.id + " " + this.isAdmin;
	}
}