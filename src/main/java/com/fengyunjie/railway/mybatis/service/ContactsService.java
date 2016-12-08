package com.fengyunjie.railway.mybatis.service;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.fengyunjie.railway.model.Contacts;
import com.fengyunjie.railway.model.User;

@Mapper
public interface ContactsService {
	@Select("")
	public List<Contacts> getContacts();
	
	@Insert("")
	public int addContact(Contacts contacts);

	@Delete("")
	public void delContact(String username);

	@Update("")
	public void modifyContact(Contacts contacts);

	@Insert("")
	public void addSelf(User user);
}
