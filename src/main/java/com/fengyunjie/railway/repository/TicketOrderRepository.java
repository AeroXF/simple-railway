package com.fengyunjie.railway.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fengyunjie.railway.model.TicketOrder;

public interface TicketOrderRepository extends JpaRepository<TicketOrder, Long>{

	@Query("UPDATE TicketOrder SET state='N' WHERE orderPersonId=?1 AND orderNo=?2")
	void cancelTicketOrder(Long id, String orderNo);

	@Query("SELECT a FROM TicketOrder a WHERE a.orderNo=?1")
	List<TicketOrder> findTicketOrderByOrderNo(String orderNo);

	@Query("SELECT a FROM TicketOrder a WHERE a.orderPersonId=?3 AND a.timeBuyTicket BETWEEN ?1 AND ?2 AND a.state = 'P'")
	List<TicketOrder> findFinishedOrderByDate(Date date1, Date addOneDay, Long id);

	@Query("SELECT a FROM TicketOrder a WHERE a.orderPersonId=?1 AND a.state = 'Y'")
	List<TicketOrder> findUnfinishedOrder(Long id);

	@Query("SELECT orderNo, timeBuyTicket, startPos, endPos, trainTag, timeTrainStart, SUM(price) AS price FROM TicketOrder"
		+ " WHERE state=?1 AND timeBuyTicket BETWEEN ?2 AND ?3 AND orderPersonId=?4 GROUP BY orderNo")
	List<Map<String, Object>> getOrderMainInfo(char state, Date date1, Date addOneDay, Long id);

	@Query("UPDATE TicketOrder SET state='P' WHERE orderPersonId=?1 AND orderNo=?2")
	void payTicketOrder(Long id, String orderNo);

	@Query("UPDATE TicketOrder SET state='N' WHERE id=?1")
	void refundOrder(Long orderId);

	@Query("SELECT MAX(seatNo) FROM TicketOrder WHERE trainTag=?1 AND seatType=?2")
	int findMaxSeatNo(String trainTag, int i);

	@Query("SELECT MIN(seatNo) FROM TicketOrder WHERE trainTag=?1 AND state='N' AND seatType=?2 AND seatNo NOT IN "
		+ "(SELECT DISTINCT seatNo FROM TicketOrder WHERE trainTag=?1 AND state='Y' AND seatType=?2)")
	int findMinRefundedSeatNo(String trainTag, int seatType);
}
