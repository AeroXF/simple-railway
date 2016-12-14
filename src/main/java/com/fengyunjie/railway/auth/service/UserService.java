package com.fengyunjie.railway.auth.service;

import com.fengyunjie.railway.auth.model.User;

public interface UserService {
	public void save(User user);
	
	public User findByUsername(String username);

}
