public class TestApp {
	public static String login_url = "https://www.facebook.com/connect/login_success.html";
	public static String auth_url = "https://www.facebook.com/dialog/oauth? client_id=603059626403225 &redirect_uri=https://www.facebook.com/connect/login_success.html&response_type=token";
	public static String app_id = "603059626403225";
	public static String app_secret = "secret";
	public static String access_token = "";

	public static void main(String [] args) {
		FacebookAPI fbapi = new FacebookAPI(access_token);
		Group group = new Group("210656799094182");


		//for (Post post : fbapi.getPosts(group)) System.out.println(post);
		//for (Profile member : fbapi.getMembers(group)) System.out.println(member);
		//for (Document doc : fbapi.getDocs(group)) System.out.println(doc);
		//fbapi.post("test");
		//for (Comment comment : fbapi.getPosts(group).get(0).getComments()) System.out.println(comment);
		//fbapi.deleteEntity(fbapi.getPosts(group).get(0));
		//System.out.println(fbapi.likeEntity(fbapi.getPosts(group).get(0)));
		//System.out.println(fbapi.unlikeEntity(fbapi.getPosts(group).get(0)));
		//fbapi.deleteEntity(fbapi.getPosts(group).get(0).getComments().get(0));
		//fbapi.postComment(fbapi.getPosts(group).get(0), "test");
	}
}