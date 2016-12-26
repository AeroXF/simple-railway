package com.fengyunjie.railway.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fengyunjie.railway.service.TicketService;

/**
 * 提前20天生成车票
 * @author fengyunjie
 */
@Component
public class GenerateTicketJob {
	Logger log=LoggerFactory.getLogger(GenerateTicketJob.class);

	@Autowired
	private TicketService ticketService;

	@Scheduled(cron="0 0 * * *")
	public void execute(){
		log.debug("start to generate ticket...");
		
		ticketService.generateTicket();
		
	}
}
