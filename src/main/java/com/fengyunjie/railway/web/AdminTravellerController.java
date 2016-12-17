package com.fengyunjie.railway.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/traveller")
public class AdminTravellerController {

	@RequestMapping("/index")
	public String index(){
		return "admin/traveller/traveller";
	}
	
	@RequestMapping("/travellerAll")
	public String trainAdjust(){
		return "admin/traveller/travellerAll";
	}
}
