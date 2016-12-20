package com.fengyunjie.railway.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fengyunjie.railway.model.Contacts;
import com.fengyunjie.railway.service.ContactsService;

@Controller
@RequestMapping("/contacts/")
public class ContactsController {
	@Autowired
	private ContactsService contactsService;
	
	@RequestMapping(value="/index")
	public String index(){
		return "users/railway/contacts";
	}
	
	@RequestMapping(value="/add/contact", method=RequestMethod.POST)
	public @ResponseBody Object addContact(Contacts contacts){
		contactsService.addContact(contacts);
		return contactsService.getContacts();
	}
	
	@RequestMapping(value="/get/contacts")
	public @ResponseBody Object getContacts(){
		return contactsService.getContacts();
	}
	
	@RequestMapping(value="/del/{username}")
	public @ResponseBody Object delContact(@PathVariable String username){
		contactsService.delContact(username);
		return contactsService.getContacts();
	}
	
	@RequestMapping(value="/modify/{id}")
	public @ResponseBody Object modifyContact(Contacts contacts){
		contactsService.modifyContact(contacts);
		return contactsService.getContacts();
	}
}
