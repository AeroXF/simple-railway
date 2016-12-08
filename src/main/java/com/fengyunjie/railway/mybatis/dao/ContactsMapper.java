package com.fengyunjie.railway.mybatis.dao;

import java.util.List;

import com.fengyunjie.railway.model.Contacts;

public interface ContactsMapper {
	public void modifyContact(Contacts contacts);

	public List<Contacts> getContactsByUserId(int userId);
	
	public int addContact(Contacts contacts);

	public void delContactByUsername(Integer id, String username);
}