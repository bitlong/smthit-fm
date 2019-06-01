/**
 * 
 */
package com.smthit.lang.utils;

import java.util.Date;

/**
 * @author Bean
 *
 */
public class DateTest {

	/**
	 * 
	 */
	public DateTest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String strDate = "Sat Sep 01 00:00:00 CST 2018";
		Date  date = DateUtils.pareseDate(strDate, "EEE MMM dd HH:mm:ss Z yyyy", null);
		System.out.println(DateUtils.format(date));
		
	}

}
