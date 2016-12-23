package com.fengyunjie.railway.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fengyunjie.railway.model.Contacts;
import com.fengyunjie.railway.model.Ticket;
import com.fengyunjie.railway.model.TicketOrder;
import com.fengyunjie.railway.service.ContactsService;
import com.fengyunjie.railway.service.TicketOrderService;
import com.fengyunjie.railway.service.TicketService;
import com.fengyunjie.railway.utils.BizUtils;

@Controller
@RequestMapping("/ticketOrder")
public class TicketOrderController {
	@Autowired
	private TicketService ticketService;
	@Autowired
	private ContactsService contactsService;
	@Autowired
	private TicketOrderService ticketOrderService;
	
	@RequestMapping("/index")
	public ModelAndView index(HttpSession session, HttpServletRequest request, String trainTag, String queryDate, String trainNo, String startPos, String endPos){
		if(trainTag != null){
			session.setAttribute("trainTag", trainTag);
			session.setAttribute("queryDate", queryDate);
			session.setAttribute("trainNo", trainNo);
			session.setAttribute("startPos", startPos);
			session.setAttribute("endPos", endPos);
		}else{
			trainTag = (String) session.getAttribute("trainTag");
			queryDate = (String) session.getAttribute("queryDate");
			trainNo = (String) session.getAttribute("trainNo");
			startPos = (String) session.getAttribute("startPos");
			endPos = (String) session.getAttribute("endPos");
		}
		
		List<Ticket> list = ticketService.getTicketByTrainTag(trainTag);
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		//动车
		if(trainNo.startsWith("D")){
			int    orderStart = 0, 
				   orderEnd   = 0,
				   ticketFirstClass  = 10000,
				   ticketSecondClass = 10000,
				   ticketStand       = 10000;
			double priceFirstClassStart  = 0,  
				   priceFirstClassEnd    = 0,
				   priceSecondClassStart = 0, 
				   priceSecondClassEnd   = 0,
				   priceStandStart		 = 0,
				   priceStandEnd 	 	 = 0;
			for(Ticket ticket : list){
				if(ticket.getStationName().equals(startPos)){
					orderStart = ticket.getStatOrder();
					priceFirstClassStart  = ticket.getPriceFirstClass();
					priceSecondClassStart = ticket.getPriceSecondClass();
					priceStandStart       = ticket.getPriceStand();
					request.setAttribute("startTime", sdf.format(ticket.getStartTime()));
				}
				if(ticket.getStationName().equals(endPos)){
					orderEnd = ticket.getStatOrder();
					priceFirstClassEnd  = ticket.getPriceFirstClass();
					priceSecondClassEnd = ticket.getPriceSecondClass();
					priceStandEnd       = ticket.getPriceStand();
					request.setAttribute("arriveTime", sdf.format(ticket.getDeptTime()));
				}
			}
			
			for(Ticket ticket : list){
				if(ticket.getStatOrder() <= orderEnd && ticket.getStatOrder() >= orderStart){
					int ticketFirstClassCurrent  = ticket.getTicketFirstClass();
					int ticketSecondClassCurrent = ticket.getTicketSecondClass();
					int ticketStandCurrent       = ticket.getTicketStand();
					if(ticketFirstClassCurrent < ticketFirstClass){
						ticketFirstClass = ticketFirstClassCurrent;
					}
					if(ticketSecondClassCurrent < ticketSecondClass){
						ticketSecondClass = ticketSecondClassCurrent;
					}
					if(ticketStandCurrent < ticketStand){
						ticketStand = ticketStandCurrent;
					}
				}
			}
			
			request.setAttribute("ticketFirstClass", ticketFirstClass);
			request.setAttribute("ticketSecondClass", ticketSecondClass);
			request.setAttribute("ticketStand", ticketStand);
			
			if(orderEnd > orderStart){
				request.setAttribute("priceFirstClass",  priceFirstClassEnd  - priceFirstClassStart);
				request.setAttribute("priceSecondClass", priceSecondClassEnd - priceSecondClassStart);
				request.setAttribute("priceStand",       priceStandEnd       - priceStandStart);
			}
		}
		
		List<Contacts> contacts = contactsService.getContacts();
		request.setAttribute("contacts",  contacts);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("trainNo", trainNo);
		mv.addObject("startPos", startPos);
		mv.addObject("endPos", endPos);
		mv.addObject("queryDate", queryDate);
		mv.setViewName("users/ticket/ticketOrder");
		
		return mv;
	}
	
	@RequestMapping("/get/orderPayment")
	public String orderPayment(String orderNo, HttpServletRequest request, HttpSession session) throws Exception{
		if(orderNo == null){
			orderNo = (String)session.getAttribute("orderNo");
		} else{
			session.setAttribute("orderNo", orderNo);
		}
		
		List<TicketOrder> list = ticketOrderService.getTicketOrderByOrderNo(orderNo);
		
		request.setAttribute("list", list);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if(list.get(0).getTrainNo().startsWith("D")){
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
			date = sdf2.parse(list.get(0).getTrainTag().split("D")[0]);
		}
		request.setAttribute("sDate", sdf.format(date) + "(" + BizUtils.getWeekDay(date) + ")");
		request.setAttribute("trainNo", list.get(0).getTrainNo());
		Ticket ticketStart = ticketService.getTicketByTrainTagAndStation(list.get(0).getTrainTag(), list.get(0).getStartPos());
		Ticket ticketEnd = ticketService.getTicketByTrainTagAndStation(list.get(0).getTrainTag(), list.get(0).getEndPos());
		SimpleDateFormat sdf3 = new SimpleDateFormat("HH:mm");
		String specificInfo = ticketStart.getStationName() + "站(" + sdf3.format(ticketStart.getStartTime()) + ")开--" 
				+ ticketEnd.getStationName() + "站(" + sdf3.format(ticketEnd.getDeptTime()) + ")到";
		request.setAttribute("specificInfo", specificInfo);
		SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		request.setAttribute("timeBuyTicket", sdf4.format(list.get(0).getTimeBuyTicket()));
		request.setAttribute("state", list.get(0).getState());
		request.setAttribute("orderNo", orderNo);
		
		return "users/ticket/orderResult";
	}
	
	@RequestMapping(value="/add/ticketOrder", method=RequestMethod.POST)
	public @ResponseBody Object addTicketOrder(HttpServletRequest request, String ticketOrderList){
		String orderNo = ticketOrderService.addTicketOrder(ticketOrderList);
		
		return orderNo;
	}
	
	@RequestMapping("/update/payTicketOrder")
	public @ResponseBody Object payTicketOrder(String orderNo){
		ticketOrderService.payTicketOrder(orderNo);
		return "success";
	}
	
	@RequestMapping("/update/cancelTicketOrder")
	public @ResponseBody Object cancelTicketOrder(String orderNo){
		ticketOrderService.updateCancelTicketOrder(orderNo);
		
		return "success";
	}
}
