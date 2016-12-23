package com.fengyunjie.railway.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fengyunjie.railway.auth.model.User;
import com.fengyunjie.railway.auth.service.UserService;
import com.fengyunjie.railway.model.TicketOrder;
import com.fengyunjie.railway.repository.TicketOrderRepository;
import com.fengyunjie.railway.service.TicketOrderService;
import com.fengyunjie.railway.service.TicketService;
import com.fengyunjie.railway.utils.BizUtils;
import com.fengyunjie.railway.utils.SessionUtils;

@Service
@Transactional
public class TicketOrderServiceImpl implements TicketOrderService {
	@Autowired
	private UserService userService;
	@Autowired
	private TicketService ticketService;
	@Autowired
	private TicketOrderRepository ticketOrderRepository;
	@Autowired
	private ObjectMapper mapper;

	@Override
	public String addTicketOrder(String strJSON) {
		TicketOrder[] list = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateStr = sdf.format(new Date());
		String orderNo = "";
		try {
			list = mapper.readValue(strJSON, TicketOrder[].class);
			User user = userService.findByUsername(SessionUtils.getUsername());
			Assert.state(list.length > 0, "订票数量必须大于0");
			orderNo = BizUtils.generateOrderNo(dateStr, list[0].getTrainNo(), user.getCredentialNumber());
			for(TicketOrder order : list){
				order.setOrderPersonId(user.getId());
				order.setTimeBuyTicket(new Date());
				order.setOrderNo(orderNo);
				order.setState('Y');
				int seatNo = ticketService.updateTicketNumber(order);
				order.setSeatNo(seatNo);
				ticketOrderRepository.save(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderNo;
	}

	@Override
	public void updateCancelTicketOrder(String orderNo) {
		User user = userService.findByUsername(SessionUtils.getUsername());
		ticketOrderRepository.cancelTicketOrder(user.getId(), orderNo);
		
		List<TicketOrder> list = ticketOrderRepository.findTicketOrderByOrderNo(orderNo);
		TicketOrder ticketOrder = list.get(0);
		ticketService.updateCancelTicket(ticketOrder.getSeatType(), ticketOrder.getTrainTag(), ticketOrder.getStartPos(), ticketOrder.getEndPos(), list.size());
	}

	@Override
	public List<TicketOrder> getOrderFinishedByDate(String startDate, String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		List<TicketOrder> list = new ArrayList<TicketOrder>();
		User user = userService.findByUsername(SessionUtils.getUsername());
		try {
			Date date1 = sdf.parse(startDate);
			list = ticketOrderRepository.findFinishedOrderByDate(date1, BizUtils.addOneDay(endDate), user.getId());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<TicketOrder> getUnfinishedOrder() {
		User user = userService.findByUsername(SessionUtils.getUsername());
		return ticketOrderRepository.findUnfinishedOrder(user.getId());
	}

	@Override
	public List<Map<String, Object>> getOrderMainInfo(char state, String startDate, String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		User user = userService.findByUsername(SessionUtils.getUsername());
		
		List<TicketOrder> orderList    = new ArrayList<TicketOrder>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Double> cacheMap   = new HashMap<String, Double>();
		try {
			Date date1 = sdf.parse(startDate);
			orderList = ticketOrderRepository.getOrderMainInfo(state, date1, BizUtils.addOneDay(endDate), user.getId());
			
			for(TicketOrder order : orderList){
				String orderNo = order.getOrderNo();
				if(cacheMap.get(orderNo) == null){
					cacheMap.put(orderNo, order.getPrice());
				} else {
					Double price = cacheMap.get(orderNo) + order.getPrice();
					cacheMap.put(orderNo, price);
				}
			}
			
			for(TicketOrder order : orderList){
				String orderNo = order.getOrderNo();
				if(cacheMap.get(orderNo) > 0){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("orderNo", orderNo);
					map.put("timeBuyTicket", sdf.format(order.getTimeBuyTicket()));
					map.put("startPos", order.getStartPos());
					map.put("endPos", order.getEndPos());
					map.put("trainTag", order.getTrainTag());
					map.put("timeTrainStart", order.getTimeTrainStart());
					map.put("orderPerson", user.getUsername());
					map.put("price", cacheMap.get(orderNo));
					cacheMap.put(orderNo, -1.0);
					list.add(map);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public List<TicketOrder> getTicketOrderByOrderNo(String orderNo) {
		return ticketOrderRepository.findTicketOrderByOrderNo(orderNo);
	}

	@Override
	public void payTicketOrder(String orderNo) {
		User user = userService.findByUsername(SessionUtils.getUsername());
		ticketOrderRepository.payTicketOrder(user.getId(), orderNo);
	}

	@Override
	public void refundOrder(Long orderId) {
		TicketOrder order = ticketOrderRepository.findOne(orderId);
		ticketOrderRepository.refundOrder(orderId);
		
		//增加退票逻辑
		ticketService.updateCancelTicket(order.getSeatType(), order.getTrainTag(), order.getStartPos(), order.getEndPos(), 1);
	}

}
