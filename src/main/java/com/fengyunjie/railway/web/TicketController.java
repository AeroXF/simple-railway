package com.fengyunjie.railway.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fengyunjie.railway.model.Ticket;
import com.fengyunjie.railway.service.TicketService;

@Controller
@RequestMapping("/ticket/")
public class TicketController {
	@Autowired
	private TicketService ticketService;
	
	@RequestMapping("/index")
	public String index(){
		return "users/ticket/ticket";
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
}
