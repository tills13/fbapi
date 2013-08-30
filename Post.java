import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Post {
	private Group group;
	private String id; 
	private int objectId;
	private Profile from; 
	private String message; 
	private String link; 
	private String genre;
	private List<Comment> comments;
	private String createdTime;
	private int likes;

	public Post(String id, String message, String link, Profile from, Group group) {
		this.id = id;
		this.message = message;
		this.link = link;
		this.from = from;
		this.group = group;
		this.genre = parseGenre();
		this.comments = getCommentsFromServer();
	}

	public Post(String id, String message, String link, Profile from, String groupid) {
		this.id = id;
		this.message = message;
		this.link = link;
		this.from = from;
		this.group = FacebookAPI.getGroup(groupid);
		this.genre = parseGenre();
		this.comments = getCommentsFromServer();
	}

	public String moToEnglish() {
		return "idiot";
	}

	public String parseGenre() {
		String gen;
		if (message.startsWith("[")) {
			gen = message.substring(1, message.indexOf("]"));
			this.message = message.substring(message.indexOf("]") + 1, message.length());
		} else if (message.startsWith("(")) {
			gen = message.substring(1, message.indexOf(")"));
			this.message = message.substring(message.indexOf(")") + 1, message.length());
		} else {
			gen = "none";
		}

		return gen;
	}

	public String getGenre() {
		return this.genre;
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	public Group getParent() {
		return this.group;
	}

	public String getFullURL() {
		return this.group.getFullURL() + "_" + this.id;
	}


	// NET FUNCTIONS
	public List<Comment> getCommentsFromServer() {
		List<Comment> comments = new ArrayList<Comment>();
		JSONObject response = FacebookAPI.getServerResponse(FacebookAPI.base_url + getFullURL());

		if (response.has("comments")) {
			JSONObject comment_section = (JSONObject)response.get("comments");
			JSONArray commentsJson = (JSONArray)comment_section.get("data");

			for (int i = 0; i < commentsJson.length(); i++) {
				JSONObject comment = (JSONObject) commentsJson.get(i);
				String id = comment.getString("id"); 

				JSONObject fromJson = (JSONObject)comment.get("from");
				String fromId = fromJson.getString("id");
				String fromName = fromJson.getString("name");
				Profile owner = new Profile(fromName, fromId);

				String message = comment.getString("message"); 
				int likes = comment.getInt("like_count");

				comments.add(new Comment(id, owner, this, message, likes));
			}
		}

		return comments;
	}

	public ArrayList<Profile> getLikesFromServer() {
		return new ArrayList<Profile>();	
	}

	public String toString() {
		return this.genre + " " + this.message + " " + this.link;
	}
}