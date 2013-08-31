import java.util.*;

public class Feed extends BaseObject {
	public Profile user;
	public List<Object> feedItems = new ArrayList<Object>();

	public Feed(Profile user) {
		super(user.getFullId());
		this.user = user;
		//posts = FacebookAPI.getPosts()
	}

	public void addFeedItem(Object obj) {
		feedItems.add(obj);
	}

	public Post getPost(int index) throws FacebookException {
		if (feedItems.get(index) instanceof Post) return (Post)feedItems.get(index);
		else throw new FacebookException("feed item not instance of Post");
	}

	public Status getStatus(int index) throws FacebookException {
		if (feedItems.get(index) instanceof Status) return (Status)feedItems.get(index);
		else throw new FacebookException("feed item not instance of Status");
	}

	public Link getLink(int index) throws FacebookException {
		if (feedItems.get(index) instanceof Link) return (Link)feedItems.get(index);
		else throw new FacebookException("feed item not instance of Link");
	}

	public Object getFeedItem(int index) {
		return feedItems.get(index);
	} 

	public List<Object> getFeedItems() {
		return this.feedItems;
	}

	public String getFullId() {
		return this.id;
	}
}