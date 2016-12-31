package com.fengyunjie.railway.service;

import java.util.List;
import java.util.Map;

import com.fengyunjie.railway.model.Ticket;
import com.fengyunjie.railway.model.TicketOrder;

public interface TicketService {
	public List<Ticket> getTicket(String startDate, String startPos, String endPos);
	
	public Ticket getTicketByTrainTagAndStation(String trainTag, String stationName);
	
	public List<Ticket> getTicketByTrainTag(String trainTag);

	public int updateTicketNumber(TicketOrder order);

	public void updateCancelTicket(int seatType, String trainTag, String startPos, String endPos, int size);

	public void addTicket(Ticket ticket);

	public void generateTicket();

	public Map<String, Object> generateCertainTicket(String trainNo, String trainTag);
}
