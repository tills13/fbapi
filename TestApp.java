public class TestApp {
	public static String login_url = "https://www.facebook.com/connect/login_success.html";
	public static String auth_url = "https://www.facebook.com/dialog/oauth? client_id=603059626403225 &redirect_uri=https://www.facebook.com/connect/login_success.html&response_type=token";
	public static String app_id = "603059626403225";
	public static String app_secret = "secret";
	public static String access_token = "CAACEdEose0cBAMlwSVJIrONyWQa62p1Gg3h7b32PnEYWDfxi80F6L5damE0JkajgIMXfdl0msMHhLHTJRnGKhaZBXB0s9mmTUIIHcaplBt2t5yf0katpQMpwApuIQCeQ98W2ZAAHZCtdAWsmHwKZAfwD6Mlh59YIUC5joDS4Vc2gLdgJ7VmdW8DI6CD70v4ZD";

	public static void main(String [] args) {
		FacebookAPI fbapi = new FacebookAPI(access_token);
		Group group = new Group("210656799094182");

		try {
			//for (Post post : fbapi.getPosts(group)) System.out.println(post);
			//for (Profile member : fbapi.getMembers(group)) System.out.println(member);
			//for (Document doc : fbapi.getDocs(group)) System.out.println(doc);
			//fbapi.post("test");
			//for (Comment comment : fbapi.getPosts(group).get(0).getComments()) System.out.println(comment);
			//fbapi.deleteEntity(fbapi.getPosts(group).get(0));
			System.out.println(fbapi.likeEntity(fbapi.getPosts(group).get(0)));
			//System.out.println(fbapi.unlikeEntity(fbapi.getPosts(group).get(0)));
			//fbapi.deleteEntity(fbapi.getPosts(group).get(0).getComments().get(0));
			//fbapi.postComment(fbapi.getPosts(group).get(0), "test");
			//System.out.println(fbapi.getProfile("691125741"));
			//System.out.println(fbapi.getComment("251524131531060_708357045847764_708389565844512"));
			//System.out.println(fbapi.getComment("251524131531060_708357045847764_708389565844512").getParent());
			//System.out.println(fbapi.getComment("251524131531060_708357045847764_708389565844512").getParent().getParent());
			//System.out.println(fbapi.getEvent("167791226745858"));
			//System.out.println(fbapi.getEvent("167791226745858").getInvited().toString());
			//System.out.println(fbapi.getEvent("167791226745858").getAttending().toString());
			//for (Object obj : fbapi.getFeed(fbapi.getProfile("649050307")).getFeedItems()) System.out.println(obj.toString());
			//System.out.println(fbapi.getFeed(fbapi.getProfile("649050307")).getStatus(1));
			//for (Comment comment : fbapi.getFeed(fbapi.getProfile("649050307")).getStatus(1).getComments()) System.out.println(comment);
		} catch (Exception e) {
			if (e instanceof FacebookException) System.out.println("fb: " + e.getMessage());
		}
	}
}