import java.util.List;

public class Group {
	public String id; 
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

	public Group(String id, String name, String description, String privacy, String email, Profile owner) {
		this.id = id;
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
		return FacebookAPI.getPosts(this);
	} 

	public List<Profile> getMembers() {
		return FacebookAPI.getMembers(this);
	}

	public String getFullURL() {
		return this.id;
	}

	public String getDescription() {
		return this.description;
	}

	public String toString() {
		return this.name + ": " + this.description;
	}
}