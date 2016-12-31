package com.fengyunjie.railway.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fengyunjie.railway.model.Ticket;
import com.fengyunjie.railway.service.TicketService;
import com.fengyunjie.railway.utils.SessionUtils;

@Controller
@RequestMapping("/ticket")
public class TicketController {
	@Autowired
	private TicketService ticketService;
	
	@RequestMapping(value={"/", "/index"}, method=RequestMethod.GET)
	public ModelAndView index(ModelAndView mv){
		mv.addObject("name", SessionUtils.getUsername());
		mv.setViewName("users/ticket/ticket");
		return mv;
	}
	
	@RequestMapping("/getTicket")
	public @ResponseBody Object getTicket(String startDate, String startPos, String endPos) {
		List<Ticket> list = ticketService.getTicket(startDate, startPos, endPos);
		return list;
	}
	
	@RequestMapping("/getTicketByTrainTag")
	public @ResponseBody Object getTicketByTrainTag(String trainTag) {
		List<Ticket> list = ticketService.getTicketByTrainTag(trainTag);
		return list;
	}
	
	@RequestMapping("/generateTicket")
	public @ResponseBody Object generateTicket(String trainNo, String trainTag){
		Map<String, Object> map = ticketService.generateCertainTicket(trainNo, trainTag);
		
		return map;
	}
	
}
