package com.fengyunjie.railway.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fengyunjie.railway.auth.model.User;
import com.fengyunjie.railway.auth.service.SecurityService;
import com.fengyunjie.railway.auth.service.UserService;
import com.fengyunjie.railway.auth.validator.UserValidator;
import com.fengyunjie.railway.utils.SessionUtils;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private UserValidator userValidator;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registration(){
		return "register";
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model){
		userValidator.validate(userForm, bindingResult);
		
		if(bindingResult.hasErrors()){
			return "register";
		}
		
		userService.save(userForm);
		
		securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
		
		return "redirect:/home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout){
		if(error != null){
			model.addAttribute("error", "Your username and password is invalid.");
		}
		
		if(logout != null){
			model.addAttribute("", "You have been logged out successfully。");
		}
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
