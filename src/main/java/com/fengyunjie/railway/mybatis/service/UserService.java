package com.fengyunjie.railway.mybatis.service;

import java.util.Date;

import com.fengyunjie.railway.model.User;

public interface UserService {
	public User getUserById(int id);

	public User getUserByNameAndPassword(String name, String password);

	public User getUserByName(String username);
	
	public int addUser(User user);

	public User getSelfInfo();

	public void modifySelfInfo(Integer gender, Date birthday, String email);
}
