package com.fengyunjie.railway.model;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ===========================================================================
 * [表字段说明]
 * 车次		站点		坐票		站票		停靠时间		开车时间		ORDER	PRICE
 * D1000	上海		1000	500					8:00		0		0
 * D1000	南京		1000	500		9:00		9:05		1		110
 * D1000	武汉		1000	500		10:00		10:05		2		240
 * D1000	深圳		1000	500		11:00		11:05		3		390
 * D1000	北京		1000	500		12:00		12:05		4		500
 * D1000	广州		1000	500		13:00					5
 * 
 * [假设有人买南京->深圳 D1000票]
 * 车次		站点		坐票		站票		停靠时间		开车时间		ORDER	PRICE
 * D1000	上海		1000	500					8:00		0		0
 * D1000	南京		999		500		9:00		9:05		1		110
 * D1000	武汉		999		500		10:00		10:05		2		240
 * D1000	深圳		999		500		11:00		11:05		3		390
 * D1000	北京		1000	500		12:00		12:05		4		500
 * D1000	广州		1000	500		13:00					5		900
 * ===========================================================================
 * 
 * 车次及余票表
 * @author fengyunjie
 * @date   2016-08-07
 */
@Entity
@Table(name = "ticket")
public class Ticket extends BaseEntity {
	private String trainNo;							//车次
	private String stationName;						//站点
	private String trainTag;						//是否同一车次
	private Date   startTime;						//开车时间
	private Date   deptTime;						//停靠时间
	private int    statOrder;						//顺序
	private double priceBusiness;					//商务座价格
	private int    ticketBusiness;					//商务座
	private double priceSpecial;					//特等座价格
	private int    ticketSpecial;					//特等座
	private double priceFirstClass;					//一等座价格
	private int    ticketFirstClass;				//一等座
	private double priceSecondClass;				//二等座价格
	private int    ticketSecondClass;				//二等座
	private double priceAdvancedSoftSleeper;		//高级软卧价格
	private int    ticketAdvancedSoftSleeper;		//高级软卧
	private double priceSoftSleeper;				//软卧价格
	private int    ticketSoftSleeper;				//软卧
	private double priceHardSleeper;				//硬卧价格
	private int    ticketHardSleeper;				//硬卧
	private double priceSoftSit;					//软座价格
	private int    ticketSoftSit;					//软座
	private double priceHardSit;					//硬座价格
	private int    ticketHardSit;					//硬座
	private double priceStand;						//站票价格
	private int    ticketStand;						//站票
	
	public String getTrainNo() {
		return trainNo;
	}
	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}
	
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getDeptTime() {
		return deptTime;
	}
	public void setDeptTime(Date deptTime) {
		this.deptTime = deptTime;
	}
	
	public int getStatOrder() {
		return statOrder;
	}
	public void setStatOrder(int statOrder) {
		this.statOrder = statOrder;
	}
	
	public int getTicketBusiness() {
		return ticketBusiness;
	}
	public void setTicketBusiness(int ticketBusiness) {
		this.ticketBusiness = ticketBusiness;
	}
	
	public int getTicketSpecial() {
		return ticketSpecial;
	}
	public void setTicketSpecial(int ticketSpecial) {
		this.ticketSpecial = ticketSpecial;
	}
	
	public int getTicketFirstClass() {
		return ticketFirstClass;
	}
	public void setTicketFirstClass(int ticketFirstClass) {
		this.ticketFirstClass = ticketFirstClass;
	}
	
	public int getTicketSecondClass() {
		return ticketSecondClass;
	}
	public void setTicketSecondClass(int ticketSecondClass) {
		this.ticketSecondClass = ticketSecondClass;
	}
	
	public int getTicketAdvancedSoftSleeper() {
		return ticketAdvancedSoftSleeper;
	}
	public void setTicketAdvancedSoftSleeper(int ticketAdvancedSoftSleeper) {
		this.ticketAdvancedSoftSleeper = ticketAdvancedSoftSleeper;
	}
	
	public int getTicketSoftSleeper() {
		return ticketSoftSleeper;
	}
	public void setTicketSoftSleeper(int ticketSoftSleeper) {
		this.ticketSoftSleeper = ticketSoftSleeper;
	}
	
	public int getTicketHardSleeper() {
		return ticketHardSleeper;
	}
	public void setTicketHardSleeper(int ticketHardSleeper) {
		this.ticketHardSleeper = ticketHardSleeper;
	}
	
	public int getTicketSoftSit() {
		return ticketSoftSit;
	}
	public void setTicketSoftSit(int ticketSoftSit) {
		this.ticketSoftSit = ticketSoftSit;
	}
	
	public int getTicketHardSit() {
		return ticketHardSit;
	}
	public void setTicketHardSit(int ticketHardSit) {
		this.ticketHardSit = ticketHardSit;
	}
	
	public int getTicketStand() {
		return ticketStand;
	}
	public void setTicketStand(int ticketStand) {
		this.ticketStand = ticketStand;
	}
	
	public String getTrainTag() {
		return trainTag;
	}
	public void setTrainTag(String trainTag) {
		this.trainTag = trainTag;
	}
	
	public double getPriceBusiness() {
		return priceBusiness;
	}
	public void setPriceBusiness(double priceBusiness) {
		this.priceBusiness = priceBusiness;
	}
	
	public double getPriceSpecial() {
		return priceSpecial;
	}
	public void setPriceSpecial(double priceSpecial) {
		this.priceSpecial = priceSpecial;
	}
	
	public double getPriceFirstClass() {
		return priceFirstClass;
	}
	public void setPriceFirstClass(double priceFirstClass) {
		this.priceFirstClass = priceFirstClass;
	}
	
	public double getPriceSecondClass() {
		return priceSecondClass;
	}
	public void setPriceSecondClass(double priceSecondClass) {
		this.priceSecondClass = priceSecondClass;
	}
	
	public double getPriceAdvancedSoftSleeper() {
		return priceAdvancedSoftSleeper;
	}
	public void setPriceAdvancedSoftSleeper(double priceAdvancedSoftSleeper) {
		this.priceAdvancedSoftSleeper = priceAdvancedSoftSleeper;
	}
	
	public double getPriceSoftSleeper() {
		return priceSoftSleeper;
	}
	public void setPriceSoftSleeper(double priceSoftSleeper) {
		this.priceSoftSleeper = priceSoftSleeper;
	}
	
	public double getPriceHardSleeper() {
		return priceHardSleeper;
	}
	public void setPriceHardSleeper(double priceHardSleeper) {
		this.priceHardSleeper = priceHardSleeper;
	}
	
	public double getPriceSoftSit() {
		return priceSoftSit;
	}
	public void setPriceSoftSit(double priceSoftSit) {
		this.priceSoftSit = priceSoftSit;
	}
	
	public double getPriceHardSit() {
		return priceHardSit;
	}
	public void setPriceHardSit(double priceHardSit) {
		this.priceHardSit = priceHardSit;
	}
	
	public double getPriceStand() {
		return priceStand;
	}
	public void setPriceStand(double priceStand) {
		this.priceStand = priceStand;
	}
}
