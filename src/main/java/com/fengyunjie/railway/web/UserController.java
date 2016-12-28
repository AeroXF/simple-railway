package com.fengyunjie.railway.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fengyunjie.railway.auth.model.User;
import com.fengyunjie.railway.auth.service.UserService;
import com.fengyunjie.railway.utils.SessionUtils;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registration(){
		return "register";
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public ModelAndView registration(HttpServletRequest request, User userRegister){
		User user = userService.findByUsername(userRegister.getNickname());
		if(user != null){
			ModelAndView mv = new ModelAndView();
			mv.addObject("errMsg", "用户名已注册");
			mv.setViewName("register");
			return mv;
		}
		
		String password = userRegister.getPassword();
		userService.save(userRegister);
		
		ModelAndView mv = new ModelAndView("register");
		mv.addObject("username", userRegister.getUsername());
		mv.addObject("password", password);
		mv.addObject("errMsg", "注册成功, 页面跳转中...");
		mv.addObject("complete", "complete");
		
		return mv;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout){
		return "login";
	}
	
	@RequestMapping(value = "/login-error", method = RequestMethod.GET)
	public String loginError(Model model){
		model.addAttribute("error", true);
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(Model model, User user){
		return "redirect:/home";
	}
	
	@RequestMapping(value={"/", "/index"}, method = RequestMethod.GET)
	public String welcome(Model model){
		return "index";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(ModelAndView mv){
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String authority = userDetails.getAuthorities().toArray()[0].toString();
		
		mv.addObject("name", SessionUtils.getUsername());
		if(authority.equals("ROLE_ADMIN")){
			mv.setViewName("admin/home");
		}else{
			mv.setViewName("users/railway/main");
		}
		
		return mv;
	}
	
	@RequestMapping("/home/index")
	public ModelAndView indexPage(){	
		ModelAndView mv = new ModelAndView();
		mv.addObject("name", SessionUtils.getUsername());
		mv.setViewName("users/railway/main");
		return mv;
	}
	
}
