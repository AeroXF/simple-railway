package com.fengyunjie.railway.service;

import java.util.List;
import java.util.Map;

import com.fengyunjie.railway.model.TicketOrder;

public interface TicketOrderService {
	public String addTicketOrder(String strJSON);

	public void updateCancelTicketOrder(String orderNo);

	/**
	 * 根据查询开始时间和查询结束时间获得已完成订单
	 * @param startDate 查询开始时间
	 * @param endDate   查询结束时间
	 * @return
	 */
	public List<TicketOrder> getOrderFinishedByDate(String startDate, String endDate);

	public List<TicketOrder> getUnfinishedOrder();
	
	public List<Map<String, Object>> getOrderMainInfo(char state, String startDate, String endDate);

	public List<TicketOrder> getTicketOrderByOrderNo(String orderNo);

	public void payTicketOrder(String orderNo);

	
	/**
	 * 退票
	 * @param orderId ticketOrder表的ID号
	 */
	public void refundOrder(Long orderId);
}
