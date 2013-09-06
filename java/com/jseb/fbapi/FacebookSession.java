package com.jseb.fbapi;

public class FacebookSession {
	public String session_id;
	public boolean connected;

	protected FacebookSession() {
		
	}

	public FacebookSession getSession() {
		return connected ? this : new FacebookSession();
	}
}