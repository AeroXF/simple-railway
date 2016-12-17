package com.fengyunjie.railway.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BizUtils {
	/**
	 * 生成订单号
	 */
	public static String generateOrderNo(String dateStr, String trainNo, String credentialNum){
		return "E" + dateStr + trainNo.substring(1, trainNo.length()) + credentialNum;
	}
	
	public static String getWeekDay(Date date){
		String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if(week_index<0){
			week_index = 0;
		} 
		return weeks[week_index];
	}
	
	public static Date addOneDay(String dateStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sdf.parse(dateStr);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.DATE, 1);
			return c.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
