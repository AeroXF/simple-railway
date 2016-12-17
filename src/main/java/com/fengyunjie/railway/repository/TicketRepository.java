package com.fengyunjie.railway.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.fengyunjie.railway.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

	@Query("SELECT a FROM Ticket a  WHERE a.stationName=?3 AND a.trainNo IN (SELECT b.trainNo FROM Ticket b WHERE b.stationName=?2 AND date_format(b.startTime,'%Y-%m-%d')=?1)")
	List<Ticket> findByStartDateAndStartPosAndEndPos(String startDate, String startPos, String endPos);

	@Query
	Ticket findByTrainTagAndStationName(String trainTag, String stationName);

	@Query
	List<Ticket> findByTrainTag(String trainTag);
	
	@Modifying
	@Query("UPDATE Ticket SET ticketFirstClass = ticketFirstClass-1 WHERE trainTag=?1 AND statOrder >= ?2 AND statOrder <= ?3")
	void updateTicketFirstClass(String trainTag, int statOrder, int statOrder2);

	@Modifying
	@Query("UPDATE Ticket SET ticketSecondClass = ticketSecondClass-1 WHERE trainTag=?1 AND statOrder >= ?2 AND statOrder <= ?3")
	void updateTicketSecondClass(String trainTag, int statOrder, int statOrder2);

	@Modifying
	@Query("UPDATE Ticket SET ticketStand = ticketStand-1 WHERE trainTag=?1 AND statOrder >= ?2 AND statOrder <= ?3")
	void updateTicketStand(String trainTag, int statOrder, int statOrder2);

	@Modifying
	@Query("UPDATE Ticket SET ticketFirstClass = ticketFirstClass+?4 WHERE trainTag=?1 AND statOrder >= ?2 AND statOrder <= ?3")
	void updateCancelTicketFirstClass(String trainTag, int startOrderNo, int endOrderNo, int size);

	@Modifying
	@Query("UPDATE Ticket SET ticketSecondClass = ticketSecondClass+?4 WHERE trainTag=?1 AND statOrder >= ?2 AND statOrder <= ?3")
	void updateCancelTicketSecondClass(String trainTag, int startOrderNo, int endOrderNo, int size);

	@Modifying
	@Query("UPDATE Ticket SET ticketStand = ticketStand+?4 WHERE trainTag=?1 AND statOrder >= ?2 AND statOrder <= ?3")
	void updateCancelTicketStand(String trainTag, int startOrderNo, int endOrderNo, int size);

}
