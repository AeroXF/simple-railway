package com.fengyunjie.railway.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fengyunjie.railway.auth.model.User;
import com.fengyunjie.railway.auth.service.SecurityService;
import com.fengyunjie.railway.auth.service.UserService;
import com.fengyunjie.railway.auth.validator.UserValidator;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private UserValidator userValidator;
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model){
		model.addAttribute("userForm", new User());
		return "registration";
	}
	
	@RequestMapping(value="/registration", method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model){
		userValidator.validate(userForm, bindingResult);
		
		if(bindingResult.hasErrors()){
			return "registration";
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
	
	@RequestMapping(value={"/", "/index"}, method = RequestMethod.GET)
	public String welcome(Model model){
		return "index";
	}
}
