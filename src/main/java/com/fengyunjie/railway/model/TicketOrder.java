package com.fengyunjie.railway.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ===========================================================================================
 * 车次		座位		类型		车次标识				乘客ID	乘客名称		始发站	终点站	票价		购票时间 		开车时间
 * D1000	#1		坐票		20160807D1000		1		张三			南京		深圳		280		2016-07-01	2016-07-10
 * ===========================================================================================
 * 
 * 车票预定表
 * @author fengyunjie
 * @date   2016-08-02
 */
@Entity
@Table(name = "ticketOrder")
public class TicketOrder extends BaseEntity {
	private String orderNo;					//订单号
	private String trainNo;					//车次
	private String trainTag;				//是否同一车次
	private int    seatNo;					//座位
	private int    seatType;				//类型(0:二等座, 1:一等座, 2:无座)
	private String credentialNumber;		//乘客身份证号
	private String custName;				//乘客名称
	private String telephone;				//乘客电话
	private Long   orderPersonId;			//购买人
	private String startPos;				//始发站
	private String endPos;					//终点站
	private double price;					//票价
	private Date   timeBuyTicket;			//购票时间
	private String timeTrainStart;			//开车时间
	private char   state;					//状态('Y'已提交未支付, 'N'无效, 'P'已支付)
	
	public String getTrainNo() {
		return trainNo;
	}
	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}
	
	public int getSeatNo() {
		return seatNo;
	}
	public void setSeatNo(int seatNo) {
		this.seatNo = seatNo;
	}
	
	public int getSeatType() {
		return seatType;
	}
	public void setSeatType(int seatType) {
		this.seatType = seatType;
	}
	
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	public String getStartPos() {
		return startPos;
	}
	public void setStartPos(String startPos) {
		this.startPos = startPos;
	}
	
	public String getEndPos() {
		return endPos;
	}
	public void setEndPos(String endPos) {
		this.endPos = endPos;
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public Date getTimeBuyTicket() {
		return timeBuyTicket;
	}
	public void setTimeBuyTicket(Date timeBuyTicket) {
		this.timeBuyTicket = timeBuyTicket;
	}

	public String getCredentialNumber() {
		return credentialNumber;
	}
	public void setCredentialNumber(String credentialNumber) {
		this.credentialNumber = credentialNumber;
	}
	
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getTrainTag() {
		return trainTag;
	}
	public void setTrainTag(String trainTag) {
		this.trainTag = trainTag;
	}
	
	public String getTimeTrainStart() {
		return timeTrainStart;
	}
	public void setTimeTrainStart(String timeTrainStart) {
		this.timeTrainStart = timeTrainStart;
	}
	
	public Long getOrderPersonId() {
		return orderPersonId;
	}
	public void setOrderPersonId(Long orderPersonId) {
		this.orderPersonId = orderPersonId;
	}
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public char getState() {
		return state;
	}
	public void setState(char state) {
		this.state = state;
	}
}
