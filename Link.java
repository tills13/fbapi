public class Link {
	public String id;
	public Object parent;
	public String link;
	public Profile from;
	public String message;

	public Link(String id, Profile from, String link, String message, Object parent) {
		this.id = id;
		this.parent = parent;
		this.from = from;
		this.link = link;
		this.message = message;
	}

	public String getFullId() {
		return this.id;
	}

	public String toString() {
		return this.from.getFullName() + ": " + this.message + " " + this.link;
	}
}