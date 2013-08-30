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
			String link = post.has("link") ? (String)post.get("link") : "";

			JSONObject fromJson = (JSONObject)post.get("from");
			Profile from = new Profile((String)fromJson.get("name"), (String)fromJson.get("id"));
			
			list.add(new Post(id, message, link, from, group));
		}

		return list;
	}

	public static List<Post> getPosts(Group group, String before, String after) {
		return getPosts(group);
	}

	public static Post getPost(String postid) {
		JSONObject response = getServerResponse(base_url + postid);

		String id = response.has("id") ? (String)response.get("id") : "";
		String groupid = id.substring(0, id.indexOf("_"));
		id = id.substring(id.indexOf("_") + 1, id.length());
		String message = response.has("message") ? response.get("message").toString() : "";
		String link = response.has("link") ? (String)response.get("link") : "";

		JSONObject fromJson = (JSONObject)response.get("from");
		Profile from = new Profile((String)fromJson.get("name"), (String)fromJson.get("id"));
		
		return new Post(id, message, link, from, groupid);
	}

	public static Group getGroup(String groupid) {
		JSONObject response = getServerResponse(base_url + groupid);

		String id = response.has("id") ? response.getString("id") : "";
		String name = response.has("name") ? response.get("name").toString() : "";
		String description = response.has("description") ? response.getString("description") : "";
		String privacy = response.getString("privacy");
		String email = response.getString("email");

		JSONObject ownerJson = response.getJSONObject("owner");
		Profile owner = new Profile(ownerJson.getString("name"), ownerJson.getString("id"));
		
		return new Group(id, name, description, privacy, email, owner);
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

			Event tempEvent = new Event(id, name, location);
			tempEvent.setTimezone(time_zome);
			tempEvent.setStartAndEndTime(start_time, end_time);

			list.add(tempEvent);
		}

		return list;
	}

	public static Event getEvent(String eventid) {
		JSONObject response = getServerResponse(base_url + eventid);

		String id = response.getString("id");
		String name = response.getString("name");
		String time_zome = response.getString("timezone");
		String start_time = response.getString("start_time");
		String end_time = response.getString("end_time");
		//String location = response.getString("location");
		Location location = new Location();

		Event tempEvent = new Event(id, name, location.toString());
		tempEvent.setTimezone(time_zome);
		tempEvent.setStartAndEndTime(start_time, end_time);

		return tempEvent;
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

			list.add(new Document(subject, id, content, author, canEdit));
		}

		return list;
	}

	public static Comment getComment(String commentid) {
		JSONObject response = getServerResponse(base_url + commentid);

		String id = response.getString("id");
		String postid = id.substring(0, id.lastIndexOf("_"));
		id = id.substring(id.lastIndexOf("_") + 1, id.length());
		String message = response.getString("message");
		int likes = response.getInt("like_count");

		JSONObject authorJson = response.getJSONObject("from");
		Profile author = new Profile(authorJson.getString("name"), authorJson.getString("id"));

		return new Comment(id, author, postid, message, likes);
	}

	public static boolean likeEntity(Object obj) {
		String url, response = "";
		try {
			if (obj instanceof Post) url = ((Post)obj).getFullURL();
			else if (obj instanceof Comment) url = ((Comment)obj).getFullURL();
			else if (obj instanceof Document) url = ((Document)obj).getFullURL();
			else return false;

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
		String url, response = "";
		try {
			if (obj instanceof Post) url = ((Post)obj).getFullURL();
			else if (obj instanceof Comment) url = ((Comment)obj).getFullURL();
			else return false;

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
		String url, response = "";
		try {
			if (obj instanceof Post) url = ((Post)obj).getFullURL();
			else if (obj instanceof Comment) url = ((Comment)obj).getFullURL();
			else if (obj instanceof Event) url = ((Event)obj).getFullURL();
			else return false;

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

	public static boolean post(String message) {
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

	public static boolean postComment(Post post, String comment) {
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
	}

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