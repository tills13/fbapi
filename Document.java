public class Document {
	public String subject; 
	public String id;
	public String content;
	public Profile author;
	public boolean canEdit;

	public Document(String subject, String id, String content, Profile author, boolean canEdit) {
		this.subject = subject;
		this.id = id;
		this.content = content;
		this.author = author;
		this.canEdit = canEdit;

	}

	public String getSubject() {
		return this.subject;
	}

	public String getContent() {
		return this.content;
	}

	public Profile getAuthor() {
		return this.author;
	}

	public String getFullURL() {
		return "https://graph.facebook.com/" + id;
	}

	public String toString() {
		return this.subject + " by " + this.author;
	}
}