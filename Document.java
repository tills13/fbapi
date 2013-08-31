public class Document {
	public String id;
	public Object parent;
	public String subject; 
	public String content;
	public Profile author;
	public boolean canEdit;

	public Document(String subject, String id, String content, Profile author, boolean canEdit, Object parent) {
		this.id = id;
		this.subject = subject;
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

	public String getFullId() {
		return FacebookAPI.base_url + id;
	}

	public String toString() {
		return this.subject + " by " + this.author;
	}
}