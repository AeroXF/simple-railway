package com.fengyunjie.railway.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fengyunjie.railway.model.TicketOrder;
import com.fengyunjie.railway.service.TicketOrderService;

@Controller
@RequestMapping("/orderFinished/")
public class OrderFinishedController {
	@Autowired
	private TicketOrderService ticketOrderService;
	
	@RequestMapping("/index")
	public String index(){
		return "users/railway/orderFinished";
	}
	
	@RequestMapping("/get/orderFinished")
	public @ResponseBody Object getOrderFinished(String startDate, String endDate){
		List<TicketOrder> list = ticketOrderService.getOrderFinishedByDate(startDate, endDate);
		return list;
	}
	
	@RequestMapping("/update/refundOrder")
	public @ResponseBody Object refundOrder(Long orderId){
		ticketOrderService.refundOrder(orderId);
		return "success";
	}
}
