package com.jseb.fbapi;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.net.*;
import java.io.*;

import com.jseb.fbapi.json.*;
import com.jseb.fbapi.base.*;

public class FacebookAPI {
	private static String access_token;
	public static String base_url = "https://graph.facebook.com/";
	public static String feed_url = "/feed";
	public static String members_url = "/members";
	public static String docs_url = "/docs";
	public static String comments_url = "/comments";
	public static String likes_url = "/likes";
	public static String events_url = "/events";


	public FacebookAPI(String access_token) {
		this.access_token = access_token;
	}

	// ------------------------
	// Groups
	// ------------------------

	public static List<Post> getGroupPosts(Group group) {
		List<Post> posts = new ArrayList<Post>();
		JSONArray posts_json = (JSONArray) getServerResponse(base_url + group.getId() + feed_url).get("data");

		for (int i = 0; i < posts_json.length(); i++) {
			JSONObject post = (JSONObject) posts_json.get(i);

			String id = post.has("id") ? (String)post.getString("id") : "";
			id = id.substring(id.indexOf("_") + 1, id.length());
			String message = post.has("message") ? post.getString("message") : "";
			Profile author = getAuthor(post.getJSONObject("from"));
			
			posts.add(new Post(id, message, author, group));
		}

		return posts;
	}

	public static List<Post> getGroupPosts(Group group, String before, String after) {
		return getGroupPosts(group);
	}

	public static List<Profile> getGroupMembers(Group group) {
		List<Profile> members = new ArrayList<Profile>();
		JSONArray members_json = (JSONArray) getServerResponse(base_url + group.getId() + members_url).get("data");

		for (int i = 0; i < members_json.length(); i++) {
			JSONObject member = (JSONObject) members_json.get(i);

			String name = member.getString("name");
			boolean admin = member.getBoolean("administrator");
			String id = member.getString("id");

			members.add(new Profile(name, id, admin)); 
		}

		return members;
	}

	public static List<Event> getGroupEvents(Group group) {
		List<Event> events = new ArrayList<Event>();
		JSONArray events_json = (JSONArray) getServerResponse(base_url + group.getId() + events_url).get("data");

		for (int i = 0; i < events_json.length(); i++) {
			JSONObject event = (JSONObject) events_json.get(i);

			String id = event.getString("id");
			String name = event.getString("name");
			String time_zome = event.getString("timezome");
			String start_time = event.getString("start_time");
			String end_time = event.getString("end_time");
			String location = event.getString("location");

			Event tempEvent = new Event(id, name, location, group);
			tempEvent.setTimezone(time_zome);
			tempEvent.setStartAndEndTime(start_time, end_time);

			events.add(tempEvent);
		}

		return events;
	}

	public static List<Document> getGroupDocs(Group group) {
		List<Document> docs = new ArrayList<Document>();
		JSONArray docs_json = (JSONArray) getServerResponse(base_url + group.getId() + docs_url).get("data");

		for (int i = 0; i < docs_json.length(); i++) {
			JSONObject doc = (JSONObject) docs_json.get(i);

			String subject = doc.has("subject") ? doc.getString("subject") : ""; 
			String id = doc.has("id") ? doc.getString("id") : "";;
			String content = doc.has("message") ? doc.getString("message") : "";
			Profile author = getAuthor(doc.getJSONObject("from"));
			boolean canEdit = doc.has("can_edit") ? doc.getBoolean("can_edit") : false;

			docs.add(new Document(subject, id, content, canEdit, author, group));
		}

		return docs;
	}

	// ------------------------
	// Profiles
	// ------------------------

	public static Profile getProfile(String profileid) {
		JSONObject response = getServerResponse(base_url + profileid);

		String id = response.getString("id");
		String first_name = response.getString("first_name");
		String last_name = response.getString("last_name");
		String gender = response.getString("gender");
		String username = response.getString("username");

		return new Profile(id, first_name, last_name, gender, username);
	}

	public static Profile getAuthor(JSONObject profilejson) {
		return new Profile(profilejson.getString("name"), profilejson.getString("id"));
	}

	// ------------------------
	// Other
	// ------------------------

	public static List<Comment> getComments(Object obj) {
		String url = "";
		if (obj instanceof Post) url = ((Post)obj).getFullId();
		else if (obj instanceof Status) url = ((Status)obj).getFullId();
		else if (obj instanceof Document) url = ((Document)obj).getFullId();
		else if (obj instanceof Link) url = ((Link)obj).getFullId();

		List<Comment> comments = new ArrayList<Comment>();
		JSONObject response = FacebookAPI.getServerResponse(base_url + url);

		if (response.has("comments")) {
			JSONObject comment_section = response.getJSONObject("comments");
			JSONArray comments_json = comment_section.getJSONArray("data");

			for (int i = 0; i < comments_json.length(); i++) {
				JSONObject comment = (JSONObject) comments_json.get(i);

				String id = comment.getString("id"); 
				Profile author = getAuthor(comment.getJSONObject("from"));
				String message = comment.getString("message"); 
				int likes = comment.getInt("like_count");

				comments.add(new Comment(id, obj, message, author, likes));
			}
		}

		return comments;
	}

	public static Feed getFeed(Profile profile) {
		return new Feed(profile);
	}

	public static boolean likeEntity(Object obj) {
		String url = "", response = "";
		if (obj instanceof Post) url = ((Post)obj).getFullId();
		else if (obj instanceof Status) url = ((Status)obj).getFullId();
		else if (obj instanceof Document) url = ((Document)obj).getFullId();
		else if (obj instanceof Link) url = ((Link)obj).getFullId();

		try {
			URL urlCon = new URL(base_url + url + likes_url + "?access_token=" + access_token);
			HttpURLConnection con = (HttpURLConnection) urlCon.openConnection();
			con.setDoOutput(true); 
			con.setInstanceFollowRedirects(false); 
			con.setRequestMethod("POST");

			response = con.getResponseMessage();

			con.disconnect();
		} catch (MalformedURLException e) {
			System.out.println("url: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("io: " + e.getMessage());
		}

		return response.equals("OK");
	}

	public static boolean unlikeEntity(Object obj) {
		String url = "", response = "";
		if (obj instanceof Post) url = ((Post)obj).getFullId();
		else if (obj instanceof Status) url = ((Status)obj).getFullId();
		else if (obj instanceof Document) url = ((Document)obj).getFullId();
		else if (obj instanceof Link) url = ((Link)obj).getFullId();

		try {
			URL urlCon = new URL(base_url + url + likes_url + "?access_token=" + access_token);
			HttpURLConnection con = (HttpURLConnection) urlCon.openConnection();
			con.setDoOutput(true); 
			con.setInstanceFollowRedirects(false); 
			con.setRequestMethod("DELETE");

			response = con.getResponseMessage();

			con.disconnect();
		} catch (MalformedURLException e) {
			System.out.println("url: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("io: " + e.getMessage());
		}

		return response.equals("OK");
	}

	public static boolean deleteEntity(Object obj) {
		String url = "", response = "";
		if (obj instanceof Post) url = ((Post)obj).getFullId();
		else if (obj instanceof Status) url = ((Status)obj).getFullId();
		else if (obj instanceof Document) url = ((Document)obj).getFullId();
		else if (obj instanceof Link) url = ((Link)obj).getFullId();

		try {
			URL urlCon = new URL(base_url + url + "?access_token=" + access_token);
			HttpURLConnection con = (HttpURLConnection) urlCon.openConnection();
			con.setDoOutput(true); 
			con.setInstanceFollowRedirects(false); 
			con.setRequestMethod("DELETE");

			response = con.getResponseMessage();

			con.disconnect();
		} catch (MalformedURLException e) {
			System.out.println("url: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("io: " + e.getMessage());
		}

		return response.equals("OK");
	}

	public static Object getObjectById(String id) {
		JSONObject response = getServerResponse(base_url + id);

		if (response.has("data")) {
			JSONArray data = response.getJSONArray("data");
			
		} else {
			if (response.has("")) {

			} else if (response.has("")) {
					
			}
		}

		return null;
	}

	/*public static boolean post(Object target, String message) {
		try {
			URL url = new URL(base_url + feed_url + "?access_token=" + access_token);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setInstanceFollowRedirects(true); 
			con.setRequestProperty("message", "test");
			//con.setRequestProperty("link", "http://www.google.com");
			con.setRequestMethod("POST");

			System.out.println(con.getRequestProperties());

			System.out.println(con.getResponseMessage());

			con.disconnect();
		} catch (MalformedURLException e) {
			System.out.println("url: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("io: " + e.getMessage());
		}

		return true;
	}

	public static boolean postComment(Object target, String comment) {
		String response = "";

		try {
			String params = "?access_token=" + access_token;
			URL url = new URL(base_url + post.getFullURL() + "/comments" + params);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true); 
			con.setInstanceFollowRedirects(false); 
			con.setRequestProperty("message", comment);
			con.setRequestMethod("POST");

			response = con.getResponseMessage();

			con.disconnect();
		} catch (MalformedURLException e) {
			System.out.println("url: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("io: " + e.getMessage());
		}

		return response.equals("OK");
	}*/

	public static JSONObject getServerResponse(String full_url) {
		try {
			URL url = new URL(full_url + "?access_token=" + access_token);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();

			Scanner in = new Scanner(connection.getInputStream());
			StringBuffer buf = new StringBuffer();

			while (in.hasNext()) buf.append(in.nextLine());

			return new JSONObject(buf.toString());
		} catch (MalformedURLException e) {
		} catch (IOException e) {
			System.out.println("io: " + e.getMessage());
		}

		return null;
	}
}