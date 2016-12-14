package com.fengyunjie.railway.auth.service;

public interface SecurityService {
	public String findLoggedInUsername();
	
	public void autoLogin(String username, String password);

}
