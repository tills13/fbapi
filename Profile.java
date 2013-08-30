public class Profile {
	public String name; 
	public String id;
	public boolean isAdmin;

	public Profile(String name, String id) {
		this.name = name;
		this.id = id;
		this.isAdmin = false;
	}

	public Profile(String name, String id, boolean isAdmin) {
		this(name, id);
		this.isAdmin = isAdmin;
	}

	public String getName() {
		return this.name;
	}

	public String getProfile() {
		return "";
	}

	public String toString() {
		return this.name + " " + this.id + " " + this.isAdmin;
	}
}