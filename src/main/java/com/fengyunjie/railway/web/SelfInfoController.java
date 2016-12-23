package com.fengyunjie.railway.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fengyunjie.railway.auth.model.User;
import com.fengyunjie.railway.auth.service.UserService;
import com.fengyunjie.railway.utils.SessionUtils;


@Controller
@RequestMapping("/selfInfo/")
public class SelfInfoController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/index")
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("users/railway/selfInfo");
		mv.addObject("name", SessionUtils.getUsername());
		return mv;
	}
	
	@RequestMapping("/get/selfInfo")
	public @ResponseBody Object getSelfInfo(){
		User user = userService.getSelfInfo();
		return user;
	}
	
	@RequestMapping("/modify/selfInfo")
	public @ResponseBody Object modifySelfInfo(Integer gender, @DateTimeFormat(pattern="yyyy-MM-dd")Date birthday, String email){
		userService.modifySelfInfo(gender, birthday, email);
		return "success";
	}
}
