package com.fengyunjie.railway.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fengyunjie.railway.utils.SessionUtils;

@Controller
public class HomeController {

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home(ModelAndView mv){
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String authority = userDetails.getAuthorities().toArray()[0].toString();
		
		mv.addObject("name", SessionUtils.getUsername());
		if(authority.equals("ROLE_ADMIN")){
			mv.setViewName("admin/home");
		}else{
			mv.setViewName("users/home");
		}
		
		return mv;
	}
}
