package com.jseb.fbapi.base;

import java.util.List;

import com.jseb.fbapi.*;

public interface Likeable {
	public void like();
	public void unlike();
	public List<Profile> getLikes();
	public String getFullId();
}