package com.fengyunjie.railway.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myInsurance/")
public class MyInsuranceController {
	
	@RequestMapping(value="/index")
	public String index(){
		return "users/railway/myInsurance";
	}
	
}
