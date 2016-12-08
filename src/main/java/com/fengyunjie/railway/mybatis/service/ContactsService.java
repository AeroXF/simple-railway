package com.fengyunjie.railway.mybatis.service;

import java.util.List;

import com.fengyunjie.railway.model.Contacts;
import com.fengyunjie.railway.model.User;

public interface ContactsService {
	public List<Contacts> getContacts();
	
	public int addContact(Contacts contacts);

	public void delContact(String username);

	public void modifyContact(Contacts contacts);

	public void addSelf(User user);
}
