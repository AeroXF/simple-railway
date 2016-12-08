package com.fengyunjie.railway.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fengyunjie.railway.mybatis.service.UserService;

public class UserServiceValidator implements UserDetailsService {
	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username ) throws UsernameNotFoundException {
		UserDetails userDetails = null;
		
		com.fengyunjie.railway.model.User user = userService.getUserByName(username);
		
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		list.add(new SimpleGrantedAuthority(user.getAuthority()));
		userDetails = new User(username, user.getPassword(), list);
		return userDetails;
	}

}
