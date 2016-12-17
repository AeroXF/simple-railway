package com.fengyunjie.railway.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/stat")
public class AdminStatController {
	
	@RequestMapping("/index")
	public String index(){
		return "admin/stat/stat";
	}
	
	@RequestMapping("/staffAnalyze")
	public String trainAdjust(){
		return "admin/stat/staffAnalyze";
	}
}
