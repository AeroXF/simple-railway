package com.fengyunjie.railway.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/message")
public class AdminMessageController {

	@RequestMapping("/index")
	public String index(){
		return "admin/message/message";
	}
	
	@RequestMapping("/msgOnline")
	public String trainAdjust(){
		return "admin/message/msgOnline";
	}
}
