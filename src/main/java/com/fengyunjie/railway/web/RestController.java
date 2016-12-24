package com.fengyunjie.railway.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fengyunjie.railway.auth.model.User;
import com.fengyunjie.railway.auth.service.UserService;

@Controller
@RequestMapping("/rest/")
public class RestController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/getUserByName", method = RequestMethod.GET)
	public @ResponseBody Object getUserByName(String nickname){
		User user = userService.findByUsername(nickname);
		if(user == null){
			return "notFound";
		}else{
			return "found";
		}
	}
}
