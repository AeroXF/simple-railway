package com.fengyunjie.railway.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.fengyunjie.railway.model.Ticket;
import com.fengyunjie.railway.model.TicketOrder;
import com.fengyunjie.railway.repository.TicketOrderRepository;
import com.fengyunjie.railway.repository.TicketRepository;
import com.fengyunjie.railway.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private TicketOrderRepository ticketOrderRepository;

	@Override
	public List<Ticket> getTicket(String startDate, String startPos, String endPos) {
		List<Ticket> list = ticketRepository.findByStartDateAndStartPosAndEndPos(startDate, startPos, endPos);
		for(Ticket ticket : list){
			Ticket tempTicket = ticketRepository.findByTrainTagAndStationName(ticket.getTrainTag(), startPos);
			ticket.setStartTime(tempTicket.getStartTime());
		}
		return list;
	}

	@Override
	public Ticket getTicketByTrainTagAndStation(String trainTag, String stationName) {
		Ticket ticket = ticketRepository.findByTrainTagAndStationName(trainTag, stationName);
		return ticket;
	}

	@Override
	public List<Ticket> getTicketByTrainTag(String trainTag) {
		List<Ticket> list = ticketRepository.findByTrainTag(trainTag);
		return list;
	}

	@Override
	public int updateTicketNumber(TicketOrder order) {
		String trainTag = order.getTrainTag();
		String startPos = order.getStartPos();
		String endPos = order.getEndPos();
		
		List<Ticket> list = ticketRepository.findByTrainTag(trainTag);
		
		int statOrderStart = -1, statOrderEnd = -1, ticketSecondMin = 10000;
		for(Ticket ticket : list){
			String stationName = ticket.getStationName();
			if(stationName.equals(startPos)){
				statOrderStart = ticket.getStatOrder();
			}
			if(stationName.equals(endPos)){
				statOrderEnd = ticket.getStatOrder();
			}
		}
		
		//如果为二等座且座位数为0则转换为站票
		if(order.getSeatType()== 0){
			for(Ticket ticket : list){
				int statOrder = ticket.getStatOrder();
				if(statOrder >= statOrderStart && statOrder <= statOrderEnd && ticket.getTicketSecondClass() >= ticketSecondMin){
					ticketSecondMin = ticket.getTicketSecondClass();
				}
			}
			if(ticketSecondMin == 0){
				order.setSeatType(2);
			}
		}
		
		int seatNo = updateTicketLeft(statOrderStart, statOrderEnd, order);

		return seatNo;
	}
	
	//更新余票, 返回坐位号
	private int updateTicketLeft(int statOrderStart, int statOrderEnd, TicketOrder order) {
		List<Ticket> list = ticketRepository.findByTrainTag(order.getTrainTag());
		
		int maxSeatNo = -1;
		boolean needRefundedTicket = false;
		int seatType = order.getSeatType();
		
		for(Ticket ticket : list){
			int statOrder = ticket.getStatOrder();
			
			if(statOrder >= statOrderStart && statOrder <= statOrderEnd){
				if(seatType == 0){
					//二等座
					int ticketLeft = ticket.getTicketSecondClass();
					
					Assert.state(ticketLeft > 0, "剩余二等座票数必须大于0");
					
					ticketRepository.updateTicketSecondClass(order.getTrainTag(), statOrder, statOrder);
					
					int curMaxSeatNo = ticketOrderRepository.findMaxSeatNo(order.getTrainTag(), 0) + 1;
					
					if(curMaxSeatNo > maxSeatNo){
						maxSeatNo = curMaxSeatNo;
					}
					
					//需要从退票中进行分配
					if(curMaxSeatNo == 1001){
						needRefundedTicket = true;
					}
				}else if(seatType == 1){
					//一等座
					int ticketLeft = ticket.getTicketFirstClass();
					
					Assert.state(ticketLeft > 0, "剩余一等座票数必须大于0");
					
					ticketRepository.updateTicketFirstClass(order.getTrainTag(), statOrder, statOrder);
					
					int curMaxSeatNo = ticketOrderRepository.findMaxSeatNo(order.getTrainTag(), 1) + 1;
					
					if(curMaxSeatNo > maxSeatNo){
						maxSeatNo = curMaxSeatNo;
					}
					
					if(curMaxSeatNo == 301){
						needRefundedTicket = true;
					}
				}else if(seatType == 2){
					//站票
					int ticketLeft = ticket.getTicketStand();
					
					Assert.state(ticketLeft > 0, "剩余站票数必须大于0");
					
					ticketRepository.updateTicketStand(order.getTrainTag(), statOrder, statOrder);
					
					int curMaxSeatNo = ticketOrderRepository.findMaxSeatNo(order.getTrainTag(), 2) + 1;
					
					if(curMaxSeatNo > maxSeatNo){
						maxSeatNo = curMaxSeatNo;
					}
					
					if(curMaxSeatNo == 501){
						needRefundedTicket = true;
					}
				}
			}
		}
		
		//从退票中进行分配
		if(needRefundedTicket){
			for(Ticket ticket : list){
				int statOrder = ticket.getStatOrder();

				if(statOrder >= statOrderStart && statOrder <= statOrderEnd){
					maxSeatNo = ticketOrderRepository.findMinRefundedSeatNo(order.getTrainTag(), seatType);
				}
			}
		}
		
		return maxSeatNo;
	}

	@Override
	public void updateCancelTicket(int seatType, String trainTag, String startPos, String endPos, int size) {
		List<Ticket> list = ticketRepository.findByTrainTag(trainTag);
		int startOrderNo = -1, endOrderNo = -1;
		for(Ticket ticket : list){
			if(ticket.getStationName().equals(startPos)){
				startOrderNo = ticket.getStatOrder();
			}
			if(ticket.getStationName().equals(endPos)){
				endOrderNo = ticket.getStatOrder();
			}
		}
		
		if(seatType == 0){
			ticketRepository.updateCancelTicketSecondClass(trainTag, startOrderNo, endOrderNo, size);
		} else if(seatType == 1){
			ticketRepository.updateCancelTicketFirstClass(trainTag, startOrderNo, endOrderNo, size);
		} else if(seatType == 2){
			ticketRepository.updateCancelTicketStand(trainTag, startOrderNo, endOrderNo, size);
		}
	}

	@Override
	public void addTicket(Ticket ticket) {
		ticketRepository.save(ticket);
	}

}
