public class Comment {
	public String id; 
	public Post post;
	public Profile author;
	public String message;	
	public int likes;

	public Comment(String id, Profile author, Post post, String message, int likes) {
		this.id = id;
		this.author = author;
		this.post = post;
		this.message = message;
		this.likes = likes;
	}

	public Comment(String id, Profile author, String postid, String message, int likes) {
		this.id = id;
		this.author = author;
		this.post = FacebookAPI.getPost(postid);
		this.message = message;
		this.likes = likes;
	}

	public void incrementLikes() {
		this.likes++;
	}

	public void decrementLikes() {
		this.likes = (this.likes == 0) ? 0 : this.likes--;
	}

	public void editMessage(String newMessage) {
		this.message = newMessage;
	}

	public Post getParent() {
		return this.post;
	}

	public String getFullURL() {
		return this.post.getFullURL() + "_" + this.id;
	}

	public String toString() {
		return this.author.getFullName() + ": " + this.message;
	}
}