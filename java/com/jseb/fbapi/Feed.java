package com.jseb.fbapi;

import java.util.*;

import com.jseb.fbapi.json.*;
import com.jseb.fbapi.base.*;

public class Feed implements Idable {
	public String id;
	public Profile user;
	public List<Object> feed_items = new ArrayList<Object>();

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

	public Object getFeedItem(int index) {
		return feed_items.get(index);
	} 

	public List<Object> getFeedItems() {
		refresh();
		return this.feed_items;
	}

	public void refresh() {
		JSONArray feed_json = FacebookAPI.getServerResponse(user.getFullId() + "/home").getJSONArray("data");			
		Object feed_item = null;

		for (int i = 0; i < feed_json.length(); i++) {
			JSONObject feed_item_json = (JSONObject)feed_json.get(i);
			

			Profile author = FacebookAPI.getAuthor(feed_item_json.getJSONObject("from"));
			String message = feed_item_json.has("message") ? feed_item_json.getString("message") : "";
			String id = feed_item_json.getString("id");

			switch(feed_item_json.getString("type")) {
				case "status": 
					feed_item = new Status(id, message, author, this);
					break;
				case "link":
					String link = feed_item_json.getString("link");
					feed_item = new Link(id, link, message, author, this);
					break;
				case "post":
					feed_item = new Post(id, message, author, this);
					break;
			}

			if (feed_item != null) feed_items.add(feed_item);
		}
	}

	public String getFullId() {
		return this.id;
	}
}