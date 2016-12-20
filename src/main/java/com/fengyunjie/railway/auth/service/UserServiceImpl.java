package com.fengyunjie.railway.auth.service;

import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengyunjie.railway.auth.model.User;
import com.fengyunjie.railway.auth.repository.RoleRepository;
import com.fengyunjie.railway.auth.repository.UserRepository;
import com.fengyunjie.railway.utils.SessionUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	//@Autowired
	//private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public void save(User user){
		user.setPassword(user.getPassword());
		user.setRoles(new HashSet<>(roleRepository.findAll()));
		userRepository.save(user);
	}
	
	@Override
	public User findByUsername(String username){
		return userRepository.findByUsername(username);
	}

	@Override
	public User getSelfInfo() {
		User user = userRepository.findByUsername(SessionUtils.getUsername());
		return user;
	}

	@Override
	public void modifySelfInfo(Integer gender, Date birthday, String email) {
		User user = getSelfInfo();
		user.setGender(gender);
		user.setBirthday(birthday);
		user.setEmail(email);
		userRepository.save(user);
	}
}
