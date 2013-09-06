package com.jseb.fbapi;

import java.util.*;

import com.jseb.fbapi.json.*;
import com.jseb.fbapi.base.*;

public class Feed implements Idable {
	public String id;
	public Profile user;
	public List<Idable> feed_items = new ArrayList<Idable>();

	public Feed(Profile user) {
		this.id = user.getFullId();
		this.user = user;
		refresh();
	}

	public Post getPost(int index) throws FacebookException {
		if (feed_items.get(index) instanceof Post) return (Post)feed_items.get(index);
		else throw new FacebookException("feed item not instance of Post");
	}

	public Status getStatus(int index) throws FacebookException {
		if (feed_items.get(index) instanceof Status) return (Status)feed_items.get(index);
		else throw new FacebookException("feed item not instance of Status");
	}

	public Link getLink(int index) throws FacebookException {
		if (feed_items.get(index) instanceof Link) return (Link)feed_items.get(index);
		else throw new FacebookException("feed item not instance of Link");
	}

	public Idable getFeedItem(int index) {
		return feed_items.get(index);
	} 

	public List<Idable> getFeed() {
		refresh();
		return this.feed_items;
	}

	public void refresh() {
		JSONArray feed_json = FacebookAPI.getServerResponse(user.getFullId() + FacebookAPI.home_url).getJSONArray("data");			

		for (int i = 0; i < feed_json.length(); i++) {
			JSONObject feed_item_json = (JSONObject)feed_json.get(i);
			
			Profile author = FacebookAPI.getAuthor(feed_item_json.getJSONObject("from"));
			String message = feed_item_json.has("message") ? feed_item_json.getString("message") : "";
			String id = feed_item_json.getString("id");

			switch(feed_item_json.getString("type")) {
				case "status": 
					feed_items.add(new Status(id, message, author, this));
					break;
				case "link":
					String link = feed_item_json.getString("link");
					feed_items.add(new Link(id, link, message, author, this));
					break;
				case "post":
					feed_items.add(new Post(id, message, author, this));
					break;
			}
		}
	}

	public String getFullId() {
		return this.id;
	}
}