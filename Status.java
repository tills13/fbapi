import java.util.List;

public class Status {
	private String id;	
	private Profile from; 
	private String message;
	private String link; 
	private List<Comment> comments;
	private String createdTime;
	private int likes;
	private Object parent;

	public Status(String id, Profile from, String message, Object parent) {
		this.id = id;
		this.parent = parent;
		this.from = from;
		this.message = message;
		this.comments = FacebookAPI.getComments(this);
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	public String getFullId() {
		return this.id;
	}

	public String toString() {
		return this.from + ": " + this.message;
	}
}