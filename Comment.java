public class Comment {
	public String id; 
	public Post post;
	public Profile owner;
	public String message;	
	public int likes;

	public Comment(String id, Profile owner, Post post, String message, int likes) {
		this.id = id;
		this.owner = owner;
		this.post = post;
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

	public String getFullURL() {
		return this.post.getFullURL() + "_" + this.id;
	}

	public String toString() {
		return this.owner.getName() + ": " + this.message;
	}
}