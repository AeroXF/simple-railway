package com.fengyunjie.railway.service;

import java.util.List;

import com.fengyunjie.railway.auth.model.User;
import com.fengyunjie.railway.model.Contacts;

public interface ContactsService {
	public List<Contacts> getContacts();
	
	public Long addContact(Contacts contacts);

	public void delContact(String username);

	public void modifyContact(Contacts contacts);

	public void addSelf(User user);
}
