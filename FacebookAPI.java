import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.net.*;
import java.io.*;

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

	public static List<Post> getPosts(Group group) {
		List<Post> list = new ArrayList<Post>();
		JSONArray posts = (JSONArray)getServerResponse(base_url + group.getId() + feed_url).get("data");

		for (int i = 0; i < posts.length(); i++) {
			JSONObject post = (JSONObject) posts.get(i);

			String id = post.has("id") ? (String)post.get("id") : "";
			id = id.substring(id.indexOf("_") + 1, id.length());
			String message = post.has("message") ? post.get("message").toString() : "";

			JSONObject fromJson = (JSONObject)post.get("from");
			Profile from = new Profile((String)fromJson.get("name"), (String)fromJson.get("id"));
			
			list.add(new Post(id, message, from, group));
		}

		return list;
	}

	public static List<Post> getPosts(Group group, String before, String after) {
		return getPosts(group);
	}

	public static List<Profile> getMembers(Group group) {
		List<Profile> list = new ArrayList<Profile>();
		JSONArray members = (JSONArray)getServerResponse(base_url + group.getId() + members_url).get("data");

		for (int i = 0; i < members.length(); i++) {
			JSONObject member = (JSONObject) members.get(i);
			String name = member.getString("name");
			boolean admin = member.getBoolean("administrator");
			String id = member.getString("id");

			list.add(new Profile(name, id, admin)); 
		}

		return list;
	}

	public static Profile getProfile(String profileid) {
		JSONObject response = getServerResponse(base_url + profileid);

		String id = response.getString("id");
		String first_name = response.getString("first_name");
		String last_name = response.getString("last_name");
		String gender = response.getString("gender");
		String username = response.getString("username");

		return new Profile(id, first_name, last_name, gender, username);
	}

	public static List<Event> getEvents(Group group) {
		List<Event> list = new ArrayList<Event>();
		JSONArray events = (JSONArray)getServerResponse(base_url + group.getId() + events_url).get("data");

		for (int i = 0; i < events.length(); i++) {
			JSONObject event = (JSONObject) events.get(i);

			String id = event.getString("id");
			String name = event.getString("name");
			String time_zome = event.getString("timezome");
			String start_time = event.getString("start_time");
			String end_time = event.getString("end_time");
			String location = event.getString("location");

			Event tempEvent = new Event(id, name, location, group);
			tempEvent.setTimezone(time_zome);
			tempEvent.setStartAndEndTime(start_time, end_time);

			list.add(tempEvent);
		}

		return list;
	}

	public static List<Document> getDocs(Group group) {
		List<Document> list = new ArrayList<Document>();
		JSONArray docs = (JSONArray)getServerResponse(base_url + group.getId() + docs_url).get("data");

		for (int i = 0; i < docs.length(); i++) {
			JSONObject doc = (JSONObject) docs.get(i);

			String subject = doc.has("subject") ? doc.getString("subject") : ""; 
			String id = doc.has("id") ? doc.getString("id") : "";;
			String content = doc.has("message") ? doc.getString("message") : "";

			JSONObject fromJson = (JSONObject)doc.get("from");
			Profile author = new Profile((String)fromJson.get("name"), (String)fromJson.get("id"));
			
			boolean canEdit = doc.has("can_edit") ? doc.getBoolean("can_edit") : false;

			list.add(new Document(subject, id, content, author, canEdit, group));
		}

		return list;
	}

	public static List<Comment> getComments(Object obj) {
		String url = "";
		if (obj instanceof Post) url = ((Post)obj).getFullId();
		else if (obj instanceof Status) url = ((Status)obj).getFullId();
		else if (obj instanceof Document) url = ((Document)obj).getFullId();
		else if (obj instanceof Link) url = ((Link)obj).getFullId();

		List<Comment> comments = new ArrayList<Comment>();
		JSONObject response = FacebookAPI.getServerResponse(base_url + url);

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

				comments.add(new Comment(id, owner, obj, message, likes));
			}
		}

		return comments;
	}

	public static Feed getFeed(Profile profile) {
		JSONObject response = getServerResponse(profile.getFullId() + "/home");	
		Feed feed = new Feed(profile);

		JSONArray feedJson =  response.getJSONArray("data");
		for (int i = 0; i < feedJson.length(); i++) {
			JSONObject feedItem = (JSONObject)feedJson.get(i);
			Object obj = null;

			JSONObject fromJson = feedItem.getJSONObject("from");
			Profile from = new Profile(fromJson.getString("name"), fromJson.getString("id"));
			String message = feedItem.has("message") ? feedItem.getString("message") : "";
			String id = feedItem.getString("id");

			switch(feedItem.getString("type")) {
				case "status": 
					obj = new Status(id, from, message, feed);
					break;
				case "link":
					String link = feedItem.getString("link");
					obj = new Link(id, from, link, message, feed);
					break;
				case "post":
					obj = new Post(id, message, from, feed);
					break;
			}
			feed.addFeedItem(obj);
		}

		return feed;
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