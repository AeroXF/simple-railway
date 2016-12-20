package com.fengyunjie.railway.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fengyunjie.railway.model.TicketOrder;
import com.fengyunjie.railway.service.TicketOrderService;

@Controller
@RequestMapping("/orderUnfinished/")
public class OrderUnfinishedController {
	@Autowired
	private TicketOrderService ticketOrderService;
	@Autowired
	private ObjectMapper objectMapper;
	
	@RequestMapping("/index")
	public ModelAndView index() throws Exception{
		String startDate = "2010-01-01";
		String endDate = "2020-01-01";
		List<Map<String, Object>> list = ticketOrderService.getOrderMainInfo('Y', startDate, endDate);
		ModelAndView mv = new ModelAndView("users/railway/orderUnfinished");
		mv.addObject("list", list);
		mv.addObject("jsonList", objectMapper.writeValueAsString(list));
		return mv;
	}
	
	@RequestMapping("/get/byOrderNo")
	public @ResponseBody Object getUnfinishedOrder(String orderNo){
		List<TicketOrder> list = ticketOrderService.getTicketOrderByOrderNo(orderNo);
		return list;
	}
}
