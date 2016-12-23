package com.fengyunjie.railway.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fengyunjie.railway.utils.SessionUtils;

@Controller
@RequestMapping("/expressAddress/")
public class ExpressAddressController {
	
	@RequestMapping(value="/index")
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("users/railway/expressAddress");
		mv.addObject("name", SessionUtils.getUsername());
		return mv;
	}
	
}
