package com.fengyunjie.railway.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fengyunjie.railway.model.User;
import com.fengyunjie.railway.mybatis.service.UserService;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	
	@Autowired
    @Qualifier("org.springframework.security.authenticationManager")
    protected AuthenticationManager authenticationManager;

	@RequestMapping("/")
	public String index(){
		return "index";
	}
	
	@RequestMapping("/index")
	public String doIndex(){
		return "index";
	}
	
	@RequestMapping(value="/login")
	public String doLogin(Integer login_error){
		return "login";
	}
	
	@RequestMapping("/register")
	public String showRegister(){
		return "register";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public ModelAndView doRegister(HttpServletRequest request, User userRegister){
		User user = userService.getUserByName(userRegister.getNickname());
		if(user != null){
			ModelAndView mv = new ModelAndView();
			mv.addObject("errMsg", "用户名已注册");
			mv.setViewName("register");
			return mv;
		}
		
		String password = userRegister.getPassword();
		userService.addUser(userRegister);
		
		ModelAndView mv = new ModelAndView("register");
		mv.addObject("username", userRegister.getNickname());
		mv.addObject("password", password);
		mv.addObject("errMsg", "注册成功, 页面跳转中...");
		mv.addObject("complete", "complete");
		
		return mv;
	}
	
	@RequestMapping(value="/rest/getUserByName")
	public @ResponseBody Object getUserByName(String nickname){
		User user = userService.getUserByName(nickname);
		if(user == null){
			return "notFound";
		}else{
			return "found";
		}
	}
}
