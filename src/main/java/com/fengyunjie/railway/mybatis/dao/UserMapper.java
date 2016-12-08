package com.fengyunjie.railway.mybatis.dao;

import java.util.Date;

import com.fengyunjie.railway.model.User;

public interface UserMapper {
	public User getUserById(Integer id);
	
	public User getUserByNameAndPassword(String username, String password);

	public User getUserByName(String username);
	
	public int addUser(User user);

	public void modifySelfInfo(Integer id, Integer gender, Date birthday, String email);
}
