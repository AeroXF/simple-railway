package com.fengyunjie.railway.mybatis.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fengyunjie.railway.model.User;
import com.fengyunjie.railway.mybatis.dao.UserMapper;
import com.fengyunjie.railway.mybatis.service.ContactsService;
import com.fengyunjie.railway.mybatis.service.UserService;
import com.fengyunjie.railway.utils.SessionUtils;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private ContactsService contactsService;

	public User getUserById(int id) {
		User user = userMapper.getUserById(id);
		return user;
	}

	public User getUserByNameAndPassword(String name, String password) {
		String pwd = new BCryptPasswordEncoder().encode(password);
		User user = userMapper.getUserByNameAndPassword(name, pwd);
		return user;
	}

	public User getUserByName(String username) {
		User user = userMapper.getUserByName(username);
		return user;
	}

	@Override
	public int addUser(User user) {
		int id = -1;
		try {
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			user.setAuthority("ROLE_USER");
			String cred = user.getCredentialNumber();
			String birthday = cred.substring(6, 10) + "-" + cred.substring(10, 12) + "-" + cred.substring(12, 14);
			user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
			user.setCreateTime(new Date());
			
			id = userMapper.addUser(user);
			contactsService.addSelf(user);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public User getSelfInfo() {
		return getUserByName(SessionUtils.getUsername());
	}

	@Override
	public void modifySelfInfo(Integer gender, Date birthday, String email) {
		User user = getSelfInfo();
		userMapper.modifySelfInfo(user.getId(), gender, birthday, email);
	}
}
