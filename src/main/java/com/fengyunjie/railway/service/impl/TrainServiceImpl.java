package com.fengyunjie.railway.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fengyunjie.railway.model.Ticket;
import com.fengyunjie.railway.model.Train;
import com.fengyunjie.railway.repository.TrainRepository;
import com.fengyunjie.railway.service.TicketService;
import com.fengyunjie.railway.service.TrainService;

@Service
@Transactional
public class TrainServiceImpl implements TrainService {
	@Autowired
	private TrainRepository trainRepository;
	@Autowired
	private TicketService ticketService;

	@Override
	public List<List<String>> getTrainInfo(int length, String queryTrainNo) {
		List<String> trainList = new ArrayList<String>();
		if(StringUtils.isEmpty(queryTrainNo)){
			trainList = trainRepository.getAllTrainNo();
		} else {
			int count = trainRepository.countTrainNo(queryTrainNo);
			if(count > 0){
				trainList.add(queryTrainNo);
			}
		}
		List<List<String>> resultList = new ArrayList<List<String>>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		for(String trainNo : trainList){
			Calendar c = Calendar.getInstance();
			List<String> tempList = new ArrayList<String>();
			tempList.add(trainNo);
			
			for(int i = 0; i < length; i++){
				String trainTag = sdf.format(c.getTime()) + trainNo;
				
				//List<Ticket> ticketList = ticketService.getTicketByTrainTag(trainTag);
				//String str = ticketList.size() > 0 ? 
				//		"<a class='ticket-detail-review' traintag='" + trainTag + "'>查看详情</a>" : 
				//		"<a class='ticket-generate' traintag='" + trainTag + "'>生成车票</a>";
				String str = "<a class='ticket-detail-review' traintag='" + trainTag + "'>查看详情</a>";
				tempList.add(str);
				c.add(Calendar.DAY_OF_MONTH, 1);
			}
			resultList.add(tempList);
		}
		
		return resultList;
	}

	@Override
	public void addTicketByTrainTag(String trainTag, String trainNo) {
		int index = trainTag.indexOf(trainNo);
		String date = trainTag.substring(0, index);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm");
		
		List<Train> trainList = trainRepository.findByTrainNo(trainNo);
		for(Train train: trainList){
			try {
				Ticket ticket = new Ticket();
				if(trainNo.startsWith("D")){
					//动车
					ticket.setTrainNo(trainNo);
					ticket.setStationName(train.getStationName());
					ticket.setTrainTag(trainTag);
					if(!StringUtils.isEmpty(train.getStartTime())){
						ticket.setStartTime(sdf.parse(date + " " + train.getStartTime()));
					}
					if(!StringUtils.isEmpty(train.getDeptTime())){
						ticket.setDeptTime(sdf.parse(date + " " + train.getDeptTime()));
					}
					ticket.setStatOrder(train.getStatOrder());
					ticket.setPriceFirstClass(train.getPriceFirstClass());
					ticket.setTicketFirstClass(train.getTicketFirstClass());
					ticket.setPriceSecondClass(train.getPriceSecondClass());
					ticket.setTicketSecondClass(train.getTicketSecondClass());
					ticket.setPriceStand(train.getPriceStand());
					ticket.setTicketStand(train.getTicketStand());
				} else if(trainNo.startsWith("G")){
					//高铁
					ticket.setTrainNo(trainNo);
					ticket.setStationName(train.getStationName());
					ticket.setTrainTag(trainTag);
					if(!StringUtils.isEmpty(train.getStartTime())){
						ticket.setStartTime(sdf.parse(date + " " + train.getStartTime()));
					}
					if(!StringUtils.isEmpty(train.getDeptTime())){
						ticket.setDeptTime(sdf.parse(date + " " + train.getDeptTime()));
					}
					ticket.setStatOrder(train.getStatOrder());
					ticket.setPriceBusiness(train.getPriceBusiness());
					ticket.setTicketBusiness(train.getTicketBusiness());
					ticket.setPriceFirstClass(train.getPriceFirstClass());
					ticket.setTicketFirstClass(train.getTicketFirstClass());
					ticket.setPriceSecondClass(train.getPriceSecondClass());
					ticket.setTicketSecondClass(train.getTicketSecondClass());
				}
				
				ticketService.addTicket(ticket);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void addTrain(Train train) {
		String trainNo = train.getTrainNo();
		if(trainNo.startsWith("D")){
			//动车
			train.setPriceBusiness(null);
			train.setPriceSpecial(null);
			train.setPriceAdvancedSoftSleeper(null);
			train.setPriceSoftSleeper(null);
			train.setPriceHardSleeper(null);
			train.setPriceSoftSit(null);
			train.setPriceHardSit(null);
			train.setTicketFirstClass(300);					//二等座
			train.setTicketSecondClass(1000);				//一等座
			train.setTicketStand(500);						//站票
			train.setStatOrder(train.getStatOrder() - 1);
			
			if(train.getPriceFirstClass() == null){
				train.setPriceFirstClass(0.0);
			}
			if(train.getPriceSecondClass() == null){
				train.setPriceSecondClass(0.0);
			}
			if(train.getPriceStand() == null){
				train.setPriceStand(train.getPriceSecondClass());
			}
		}else if(trainNo.startsWith("G")){
			//高铁
			train.setPriceSpecial(null); 					//特等座
			train.setPriceAdvancedSoftSleeper(null); 		//高级软卧
			train.setPriceSoftSleeper(null); 				//软卧
			train.setPriceHardSleeper(null); 				//硬卧
			train.setPriceSoftSit(null); 					//软座
			train.setPriceHardSit(null); 					//硬座
			train.setTicketFirstClass(300);					//一等座
			train.setTicketSecondClass(800);				//二等座
			train.setTicketBusiness(200);					//商务座
			train.setPriceStand(null); 						//站票
			train.setStatOrder(train.getStatOrder() - 1);
			
			if(train.getPriceBusiness() == null){
				train.setPriceBusiness(0.0);
			}
			if(train.getPriceFirstClass() == null){
				train.setPriceFirstClass(0.0);
			}
			if(train.getPriceSecondClass() == null){
				train.setPriceSecondClass(0.0);
			}
		}
		trainRepository.save(train);
	}

	@Override
	public List<Train> getAllTrain() {
		return trainRepository.findAll();
	}

	@Override
	public void modifyTrain(Train train) {
		if(train.getTrainNo().startsWith("D")){
			train.setStatOrder(train.getStatOrder() - 1);
			//int count = trainRepository.countByTrainNoAndStatOrder(train.getTrainNo(), train.getStatOrder());
			trainRepository.save(train);
		}
	}

	@Override
	public void delTrain(String trainNo) {
		List<Train> list = trainRepository.findByTrainNo(trainNo);
		trainRepository.delete(list);
	}

	@Override
	public void saveTrainList(List<Train> list) {
		trainRepository.save(list);
	}

}
