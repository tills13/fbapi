package com.jseb.fbapi.base;

import java.util.List;

import com.jseb.fbapi.*;

public interface Commentable {
	public void comment(String message);
	public List<Comment> getComments();
	public String getFullId();
}