package com.stone.privilege;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDao {
	
	private static Map<String, User> users;
	private static List<Authority> authorities = null;
	static {
		authorities = new ArrayList<>();
		authorities.add(new Authority("Article-1", "/app/article-1.jsp"));
		authorities.add(new Authority("Article-2", "/app/article-2.jsp"));
		authorities.add(new Authority("Article-3", "/app/article-3.jsp"));
		authorities.add(new Authority("Article-4", "/app/article-4.jsp"));
		
		users = new HashMap<String, User>();
		User user1 = new User("AAA", authorities.subList(0, 2));
		users.put("AAA", user1);
		
		User user2 = new User("BBB", authorities.subList(2, 4));
		users.put("BBB", user2);
	}
	
	public User get(String username) {
		
		return users.get(username);
	}
	
	public void update(String username, List<Authority> authorities) {
		users.get(username).setAuthorities(authorities);
	}
	
	public static List<Authority> getAuthorities() {
		return authorities;
	}

	public List<Authority> getAuthorities(String[] urls) {
		List<Authority> authorities2 = new ArrayList<>();
		
		for (Authority authority : authorities) {
			if (urls != null) {
				for (String url : urls) {
					if (url.equals(authority.getUrl())) {
						authorities2.add(authority);
					}
				}
			}
		}
		
		return authorities2;
	}
}