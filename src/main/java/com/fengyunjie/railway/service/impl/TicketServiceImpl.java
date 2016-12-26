package com.fengyunjie.railway.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.fengyunjie.railway.model.Ticket;
import com.fengyunjie.railway.model.TicketOrder;
import com.fengyunjie.railway.model.Train;
import com.fengyunjie.railway.repository.TicketOrderRepository;
import com.fengyunjie.railway.repository.TicketRepository;
import com.fengyunjie.railway.repository.TrainRepository;
import com.fengyunjie.railway.service.TicketService;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private TicketOrderRepository ticketOrderRepository;
	
	@Autowired
	private TrainRepository trainRepository;

	@Override
	public List<Ticket> getTicket(String startDate, String startPos, String endPos) {
		String queryStartDate = String.join("", startDate.split("-"));
		List<Ticket> list = ticketRepository.findByStartPosAndEndPosAndStartDate(startPos, endPos, queryStartDate);
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

	@Override
	public void generateTicket() {
		List<Train> list = trainRepository.findAll();
		
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 20);
		
		int year  = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int date  = c.get(Calendar.DATE);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

		for(Train train : list){
			Ticket ticket = new Ticket();
			//车次
			ticket.setTrainNo(train.getTrainNo());
			
			//站点
			ticket.setStationName(train.getStationName());
			
			//是否同一车次
			String dateStr  = "" + year + (month < 10 ? "0" + month : month) + (date < 10 ? "0" + date : date);
			String trainTag = dateStr + train.getTrainNo();
			ticket.setTrainTag(trainTag);
			
			//开车日期
			if(!StringUtils.isEmpty(train.getStartTime())){
				String[] timeArray = train.getStartTime().split(":");
				int hour = Integer.parseInt(timeArray[0]);
				int minute = Integer.parseInt(timeArray[1]);
				String startTimeStr = dateStr + (hour < 10 ? "0" + hour : hour) + (minute < 10 ? "0" + minute : minute) + "00";
				try{
					ticket.setStartTime(sdf.parse(startTimeStr));
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//停靠时间
			if(!StringUtils.isEmpty(train.getDeptTime())){
				String[] timeArray = train.getDeptTime().split(":");
				int hour = Integer.parseInt(timeArray[0]);
				int minute = Integer.parseInt(timeArray[1]);
				String deptTimeStr = dateStr + (hour < 10 ? "0" + hour : hour) + (minute < 10 ? "0" + minute : minute) + "00";
				try{
					ticket.setDeptTime(sdf.parse(deptTimeStr));
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//顺序
			ticket.setStatOrder(train.getStatOrder());
			
			//商务座价格
			ticket.setPriceBusiness(train.getPriceBusiness());
			
			//商务座
			ticket.setTicketBusiness(train.getTicketBusiness());
			
			//特等座价格
			ticket.setPriceSpecial(train.getPriceSpecial());
			
			//特等座
			ticket.setTicketSpecial(train.getTicketSpecial());
			
			//一等座价格
			ticket.setPriceFirstClass(train.getPriceFirstClass());
			
			//一等座
			ticket.setTicketFirstClass(train.getTicketFirstClass());
			
			//二等座价格
			ticket.setPriceSecondClass(train.getPriceSecondClass());
			
			//二等座
			ticket.setTicketSecondClass(train.getTicketSecondClass());
			
			//高级软卧价格
			ticket.setPriceAdvancedSoftSleeper(train.getPriceAdvancedSoftSleeper());
			
			//高级软卧
			ticket.setTicketAdvancedSoftSleeper(train.getTicketAdvancedSoftSleeper());
			
			//软卧价格
			ticket.setPriceSoftSleeper(train.getPriceSoftSleeper());
			
			//软卧
			ticket.setTicketSoftSleeper(train.getTicketSoftSleeper());
			
			//硬卧价格
			ticket.setPriceHardSleeper(train.getPriceHardSleeper());
			
			//硬卧
			ticket.setTicketHardSleeper(train.getTicketHardSleeper());
			
			//软座价格
			ticket.setPriceSoftSit(train.getPriceSoftSit());
			
			//软座
			ticket.setTicketSoftSit(train.getTicketSoftSit());
			
			//硬座价格
			ticket.setPriceHardSit(train.getPriceHardSit());
			
			//硬座
			ticket.setTicketHardSit(train.getTicketHardSit());
			
			//站票价格
			ticket.setPriceStand(train.getPriceStand());
			
			//站票
			ticket.setTicketStand(train.getTicketStand());
			
			addTicket(ticket);
		}
	}

}
