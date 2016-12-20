package com.fengyunjie.railway.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/expressAddress/")
public class ExpressAddressController {
	
	@RequestMapping(value="/index")
	public String index(){
		return "users/railway/expressAddress";
	}
	
}
