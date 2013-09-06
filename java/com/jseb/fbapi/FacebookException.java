package com.jseb.fbapi;

public class FacebookException extends Exception {
	public FacebookException() { 
		super(); 
	}

	public FacebookException(String message) { 
		super(message); 
	}

	public FacebookException(String message, Throwable cause) { 
		super(message, cause); 
	}

	public FacebookException(Throwable cause) { 
		super(cause); 
	}
}