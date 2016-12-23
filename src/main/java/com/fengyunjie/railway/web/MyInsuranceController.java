package com.fengyunjie.railway.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fengyunjie.railway.utils.SessionUtils;

@Controller
@RequestMapping("/myInsurance/")
public class MyInsuranceController {
	
	@RequestMapping(value="/index")
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("users/railway/myInsurance");
		mv.addObject("name", SessionUtils.getUsername());
		return mv;
	}
	
}
