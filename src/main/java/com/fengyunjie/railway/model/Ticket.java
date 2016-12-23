package com.fengyunjie.railway.model;


import java.util.Date;

import javax.persistence.Column;
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

	@Column(nullable=false)
	private String  trainNo;						//车次

	@Column(nullable=false)
	private String  stationName;					//站点

	@Column(nullable=false)
	private String  trainTag;						//是否同一车次

	@Column(nullable=true)
	private Date   startTime;						//开车日期

	@Column(nullable=true)
	private Date    deptTime;						//停靠时间

	@Column(nullable=false)
	private Integer statOrder;						//顺序

	@Column(nullable=true)
	private Double  priceBusiness;					//商务座价格

	@Column(nullable=true)
	private Integer ticketBusiness;					//商务座

	@Column(nullable=true)
	private Double  priceSpecial;					//特等座价格

	@Column(nullable=true)
	private Integer ticketSpecial;					//特等座

	@Column(nullable=true)
	private Double  priceFirstClass;				//一等座价格

	@Column(nullable=true)
	private Integer ticketFirstClass;				//一等座

	@Column(nullable=true)
	private Double  priceSecondClass;				//二等座价格

	@Column(nullable=true)
	private Integer ticketSecondClass;				//二等座

	@Column(nullable=true)
	private Double  priceAdvancedSoftSleeper;		//高级软卧价格

	@Column(nullable=true)
	private Integer ticketAdvancedSoftSleeper;		//高级软卧

	@Column(nullable=true)
	private Double  priceSoftSleeper;				//软卧价格

	@Column(nullable=true)
	private Integer ticketSoftSleeper;				//软卧

	@Column(nullable=true)
	private Double  priceHardSleeper;				//硬卧价格

	@Column(nullable=true)
	private Integer ticketHardSleeper;				//硬卧

	@Column(nullable=true)
	private Double  priceSoftSit;					//软座价格

	@Column(nullable=true)
	private Integer ticketSoftSit;					//软座

	@Column(nullable=true)
	private Double  priceHardSit;					//硬座价格

	@Column(nullable=true)
	private Integer ticketHardSit;					//硬座

	@Column(nullable=true)
	private Double  priceStand;						//站票价格

	@Column(nullable=true)
	private Integer ticketStand;						//站票
	
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
	
	public Integer getStatOrder() {
		return statOrder;
	}
	public void setStatOrder(Integer statOrder) {
		this.statOrder = statOrder;
	}
	
	public Integer getTicketBusiness() {
		return ticketBusiness;
	}
	public void setTicketBusiness(Integer ticketBusiness) {
		this.ticketBusiness = ticketBusiness;
	}
	
	public Integer getTicketSpecial() {
		return ticketSpecial;
	}
	public void setTicketSpecial(Integer ticketSpecial) {
		this.ticketSpecial = ticketSpecial;
	}
	
	public Integer getTicketFirstClass() {
		return ticketFirstClass;
	}
	public void setTicketFirstClass(Integer ticketFirstClass) {
		this.ticketFirstClass = ticketFirstClass;
	}
	
	public Integer getTicketSecondClass() {
		return ticketSecondClass;
	}
	public void setTicketSecondClass(Integer ticketSecondClass) {
		this.ticketSecondClass = ticketSecondClass;
	}
	
	public Integer getTicketAdvancedSoftSleeper() {
		return ticketAdvancedSoftSleeper;
	}
	public void setTicketAdvancedSoftSleeper(Integer ticketAdvancedSoftSleeper) {
		this.ticketAdvancedSoftSleeper = ticketAdvancedSoftSleeper;
	}
	
	public Integer getTicketSoftSleeper() {
		return ticketSoftSleeper;
	}
	public void setTicketSoftSleeper(Integer ticketSoftSleeper) {
		this.ticketSoftSleeper = ticketSoftSleeper;
	}
	
	public Integer getTicketHardSleeper() {
		return ticketHardSleeper;
	}
	public void setTicketHardSleeper(Integer ticketHardSleeper) {
		this.ticketHardSleeper = ticketHardSleeper;
	}
	
	public Integer getTicketSoftSit() {
		return ticketSoftSit;
	}
	public void setTicketSoftSit(Integer ticketSoftSit) {
		this.ticketSoftSit = ticketSoftSit;
	}
	
	public Integer getTicketHardSit() {
		return ticketHardSit;
	}
	public void setTicketHardSit(Integer ticketHardSit) {
		this.ticketHardSit = ticketHardSit;
	}
	
	public Integer getTicketStand() {
		return ticketStand;
	}
	public void setTicketStand(Integer ticketStand) {
		this.ticketStand = ticketStand;
	}
	
	public String getTrainTag() {
		return trainTag;
	}
	public void setTrainTag(String trainTag) {
		this.trainTag = trainTag;
	}
	
	public Double getPriceBusiness() {
		return priceBusiness;
	}
	public void setPriceBusiness(Double priceBusiness) {
		this.priceBusiness = priceBusiness;
	}
	
	public Double getPriceSpecial() {
		return priceSpecial;
	}
	public void setPriceSpecial(Double priceSpecial) {
		this.priceSpecial = priceSpecial;
	}
	
	public Double getPriceFirstClass() {
		return priceFirstClass;
	}
	public void setPriceFirstClass(Double priceFirstClass) {
		this.priceFirstClass = priceFirstClass;
	}
	
	public Double getPriceSecondClass() {
		return priceSecondClass;
	}
	public void setPriceSecondClass(Double priceSecondClass) {
		this.priceSecondClass = priceSecondClass;
	}
	
	public Double getPriceAdvancedSoftSleeper() {
		return priceAdvancedSoftSleeper;
	}
	public void setPriceAdvancedSoftSleeper(Double priceAdvancedSoftSleeper) {
		this.priceAdvancedSoftSleeper = priceAdvancedSoftSleeper;
	}
	
	public Double getPriceSoftSleeper() {
		return priceSoftSleeper;
	}
	public void setPriceSoftSleeper(Double priceSoftSleeper) {
		this.priceSoftSleeper = priceSoftSleeper;
	}
	
	public Double getPriceHardSleeper() {
		return priceHardSleeper;
	}
	public void setPriceHardSleeper(Double priceHardSleeper) {
		this.priceHardSleeper = priceHardSleeper;
	}
	
	public Double getPriceSoftSit() {
		return priceSoftSit;
	}
	public void setPriceSoftSit(Double priceSoftSit) {
		this.priceSoftSit = priceSoftSit;
	}
	
	public Double getPriceHardSit() {
		return priceHardSit;
	}
	public void setPriceHardSit(Double priceHardSit) {
		this.priceHardSit = priceHardSit;
	}
	
	public Double getPriceStand() {
		return priceStand;
	}
	public void setPriceStand(Double priceStand) {
		this.priceStand = priceStand;
	}
}
