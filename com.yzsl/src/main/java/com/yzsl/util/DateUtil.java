package com.yzsl.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * 
 * 
 */
public class DateUtil {

	public static void main(String[] args) {

	}

	/**
	 * 日期转字符串
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static String dateToString(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		}
		return "";
	}

	/**
	 * 日期转字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		return dateToString(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String getDateTime() {
		return dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String dateToString() {
		return dateToString(new Date(), "yyyy-MM-dd")+" 23:59:59";
	}
	
	
	public static String dateNowToString() {
		return dateToString(new Date(), "yyyy-MM-dd");
	}
	
	public static String dateNowToString1() {
		Date date = new Date();
		date.setHours(24);
		return dateToString(date, "yyyy-MM-dd");
	}
	
	public static String dateNowToString2() {
		Date date = new Date();
		date.setHours(24*7);
		return dateToString(date, "yyyy-MM-dd");
	}
	
	public static String dateToString2(Date date) {
		date.setDate(7);
		return dateToString(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String dateToString2() {
		Date date = new Date();
		date.setHours(-24*7);
		return dateToString(date, "yyyy-MM-dd")+" 00:00:00";
	}

}
