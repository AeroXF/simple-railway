package com.fengyunjie.railway.auth.service;

import java.util.Date;

import com.fengyunjie.railway.auth.model.User;

public interface UserService {
	public void save(User user);
	
	public User findByUsername(String username);

	public User getSelfInfo();

	public void modifySelfInfo(Integer gender, Date birthday, String email);
}
