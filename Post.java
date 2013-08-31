import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Post {
	private String id;
	private Object parent;
	private Profile from; 
	private String message; 
	private List<Comment> comments;
	private String createdTime;
	private int likes;

	public Post(String id) {
		this.id = id;
	}

	public Post(String id, String message, Profile from, Object parent) {
		this.id = id;
		this.parent = parent;
		this.message = message;
		this.from = from;
		this.comments = getCommentsFromServer();
	}

	public String moToEnglish() {
		return "idiot";
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	public Object getParent() {
		return this.parent;
	}

	public String getFullId() {
		if (this.parent instanceof Post) return ((Post)this.parent).getFullId() + "_" + this.id;
		else if (this.parent instanceof Link) return ((Group)this.parent).getFullId() + "_" + this.id;
		return this.id;
	}


	// NET FUNCTIONS
	public List<Comment> getCommentsFromServer() {
		return this.comments == null ? this.comments = FacebookAPI.getComments(this) : this.comments;
	}

	public ArrayList<Profile> getLikesFromServer() {
		return new ArrayList<Profile>();	
	}

	public String toString() {
		return this.from + " " + this.message;
	}
}