package com.fengyunjie.railway.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 列车表
 * @author fengyunjie
 * @date   2016-09-10
 */
@Entity
@Table(name="train")
public class Train extends BaseEntity {

	private String 		trainNo;						//车次
	private String 		stationName;					//站点
	private String 		startTime;						//开车时间
	private String 		deptTime;						//停靠时间
	private Integer 	statOrder;						//顺序
	private Double 		priceBusiness;					//商务座价格
	private Integer    	ticketBusiness;					//商务座
	private Double 		priceSpecial;					//特等座价格
	private Integer    	ticketSpecial;					//特等座
	private Double 		priceFirstClass;				//一等座价格
	private Integer    	ticketFirstClass;				//一等座
	private Double 		priceSecondClass;				//二等座价格
	private Integer    	ticketSecondClass;				//二等座
	private Double 		priceAdvancedSoftSleeper;		//高级软卧价格
	private Integer    	ticketAdvancedSoftSleeper;		//高级软卧
	private Double 		priceSoftSleeper;				//软卧价格
	private Integer    	ticketSoftSleeper;				//软卧
	private Double 		priceHardSleeper;				//硬卧价格
	private Integer    	ticketHardSleeper;				//硬卧
	private Double 		priceSoftSit;					//软座价格
	private Integer    	ticketSoftSit;					//软座
	private Double 		priceHardSit;					//硬座价格
	private Integer    	ticketHardSit;					//硬座
	private Double 		priceStand;						//站票价格
	private Integer    	ticketStand;					//站票
	
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
	
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	public String getDeptTime() {
		return deptTime;
	}
	public void setDeptTime(String deptTime) {
		this.deptTime = deptTime;
	}
	
	public Integer getStatOrder() {
		return statOrder;
	}
	public void setStatOrder(Integer statOrder) {
		this.statOrder = statOrder;
	}
	
	public Double getPriceBusiness() {
		return priceBusiness;
	}
	public void setPriceBusiness(Double priceBusiness) {
		this.priceBusiness = priceBusiness;
	}
	
	public Integer getTicketBusiness() {
		return ticketBusiness;
	}
	public void setTicketBusiness(Integer ticketBusiness) {
		this.ticketBusiness = ticketBusiness;
	}
	
	public Double getPriceSpecial() {
		return priceSpecial;
	}
	public void setPriceSpecial(Double priceSpecial) {
		this.priceSpecial = priceSpecial;
	}
	
	public Integer getTicketSpecial() {
		return ticketSpecial;
	}
	public void setTicketSpecial(Integer ticketSpecial) {
		this.ticketSpecial = ticketSpecial;
	}
	
	public Double getPriceFirstClass() {
		return priceFirstClass;
	}
	public void setPriceFirstClass(Double priceFirstClass) {
		this.priceFirstClass = priceFirstClass;
	}
	
	public Integer getTicketFirstClass() {
		return ticketFirstClass;
	}
	public void setTicketFirstClass(Integer ticketFirstClass) {
		this.ticketFirstClass = ticketFirstClass;
	}
	
	public Double getPriceSecondClass() {
		return priceSecondClass;
	}
	public void setPriceSecondClass(Double priceSecondClass) {
		this.priceSecondClass = priceSecondClass;
	}
	
	public Integer getTicketSecondClass() {
		return ticketSecondClass;
	}
	public void setTicketSecondClass(Integer ticketSecondClass) {
		this.ticketSecondClass = ticketSecondClass;
	}
	
	public Double getPriceAdvancedSoftSleeper() {
		return priceAdvancedSoftSleeper;
	}
	public void setPriceAdvancedSoftSleeper(Double priceAdvancedSoftSleeper) {
		this.priceAdvancedSoftSleeper = priceAdvancedSoftSleeper;
	}
	
	public Integer getTicketAdvancedSoftSleeper() {
		return ticketAdvancedSoftSleeper;
	}
	public void setTicketAdvancedSoftSleeper(Integer ticketAdvancedSoftSleeper) {
		this.ticketAdvancedSoftSleeper = ticketAdvancedSoftSleeper;
	}
	
	public Double getPriceSoftSleeper() {
		return priceSoftSleeper;
	}
	public void setPriceSoftSleeper(Double priceSoftSleeper) {
		this.priceSoftSleeper = priceSoftSleeper;
	}
	
	public Integer getTicketSoftSleeper() {
		return ticketSoftSleeper;
	}
	public void setTicketSoftSleeper(Integer ticketSoftSleeper) {
		this.ticketSoftSleeper = ticketSoftSleeper;
	}
	
	public Double getPriceHardSleeper() {
		return priceHardSleeper;
	}
	public void setPriceHardSleeper(Double priceHardSleeper) {
		this.priceHardSleeper = priceHardSleeper;
	}
	
	public Integer getTicketHardSleeper() {
		return ticketHardSleeper;
	}
	public void setTicketHardSleeper(Integer ticketHardSleeper) {
		this.ticketHardSleeper = ticketHardSleeper;
	}
	
	public Double getPriceSoftSit() {
		return priceSoftSit;
	}
	public void setPriceSoftSit(Double priceSoftSit) {
		this.priceSoftSit = priceSoftSit;
	}
	
	public Integer getTicketSoftSit() {
		return ticketSoftSit;
	}
	public void setTicketSoftSit(Integer ticketSoftSit) {
		this.ticketSoftSit = ticketSoftSit;
	}
	
	public Double getPriceHardSit() {
		return priceHardSit;
	}
	public void setPriceHardSit(Double priceHardSit) {
		this.priceHardSit = priceHardSit;
	}
	
	public Integer getTicketHardSit() {
		return ticketHardSit;
	}
	public void setTicketHardSit(Integer ticketHardSit) {
		this.ticketHardSit = ticketHardSit;
	}
	
	public Double getPriceStand() {
		return priceStand;
	}
	public void setPriceStand(Double priceStand) {
		this.priceStand = priceStand;
	}
	
	public Integer getTicketStand() {
		return ticketStand;
	}
	public void setTicketStand(Integer ticketStand) {
		this.ticketStand = ticketStand;
	}
	
	@Override
	public String toString() {
		return "trainNo: " + trainNo 
			+ ", stationName: " + stationName
			+ ", startTime: " + startTime
			+ ", deptTime: " + deptTime
			+ ", statOrder: " + statOrder
			+ ", priceBusiness: " + priceBusiness
			+ ", ticketBusiness: " + ticketBusiness
			+ ", priceSpecial: " + priceSpecial
			+ ", ticketSpecial: " + ticketSpecial
			+ ", priceFirstClass: " + priceFirstClass
			+ ", ticketFirstClass: " + ticketFirstClass
			+ ", priceSecondClass: " + priceSecondClass
			+ ", ticketSecondClass: " + ticketSecondClass
			+ ", priceAdvancedSoftSleeper: " + priceAdvancedSoftSleeper
			+ ", ticketAdvancedSoftSleeper: " + ticketAdvancedSoftSleeper
			+ ", priceSoftSleeper: " + priceSoftSleeper
			+ ", ticketSoftSleeper: " + ticketSoftSleeper
			+ ", priceHardSleeper: " + priceHardSleeper
			+ ", ticketHardSleeper: " + ticketHardSleeper
			+ ", priceSoftSit: " + priceSoftSit
			+ ", ticketSoftSit: " + ticketSoftSit
			+ ", priceHardSit: " + priceHardSit
			+ ", ticketHardSit: " + ticketHardSit
			+ ", priceStand: " + priceStand
			+ ", ticketStand: " + ticketStand;
	}
}
