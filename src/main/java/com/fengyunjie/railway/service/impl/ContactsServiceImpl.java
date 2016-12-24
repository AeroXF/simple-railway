package com.fengyunjie.railway.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengyunjie.railway.auth.model.User;
import com.fengyunjie.railway.auth.repository.UserRepository;
import com.fengyunjie.railway.model.Contacts;
import com.fengyunjie.railway.repository.ContactsRepository;
import com.fengyunjie.railway.service.ContactsService;
import com.fengyunjie.railway.utils.SessionUtils;

@Service
@Transactional
public class ContactsServiceImpl implements ContactsService {
	@Autowired
	private ContactsRepository contactsRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<Contacts> getContacts() {
		User user = userRepository.findByUsername(SessionUtils.getUsername());
		return contactsRepository.findByUserId(user.getId());
	}

	@Override
	public Long addContact(Contacts contacts) {
		User user = userRepository.findByUsername(SessionUtils.getUsername());
		contacts.setUserId(user.getId());
		Contacts c = contactsRepository.save(contacts);
		
		return c.getId();
	}

	@Override
	public void delContact(String usernameStr) {
		String[] userArray = usernameStr.split("-");
		for(String username : userArray){
			User user = userRepository.findByUsername(SessionUtils.getUsername());
			contactsRepository.deleteByUsername(user.getId(), username);
		}
	}

	@Override
	public void modifyContact(Contacts contacts) {
		contactsRepository.save(contacts);
	}

	@Override
	public void addSelf(User user) {
		Contacts contacts = new Contacts();
		contacts.setCountry(user.getCountry());
		contacts.setCredentialNumber(user.getCredentialNumber());
		contacts.setCredentialType(user.getCredentialType());
		contacts.setCustType(user.getCustType());
		contacts.setGender(user.getGender());
		contacts.setTelephone(user.getTelephone());
		contacts.setUserId(user.getId());
		contacts.setUsername(user.getUsername());
		
		contactsRepository.save(contacts);
	}

}
