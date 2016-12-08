package com.fengyunjie.railway.mybatis.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.fengyunjie.railway.model.User;

@Mapper
public interface UserMapper {
	@Select("")
	public User getUserById(Integer id);
	
	@Select("")
	public User getUserByNameAndPassword(String username, String password);

	@Select("")
	public User getUserByName(String username);
	
	@Insert("")
	public int addUser(User user);

	@Update("")
	public void modifySelfInfo(Integer id, Integer gender, Date birthday, String email);
}
