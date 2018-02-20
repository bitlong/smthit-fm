package com.smthit.lang.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.smthit.lang.exception.DataParseException;
import com.smthit.lang.exception.ServiceException;

public class DateUtils {
	public static final String DATE_SHORT = "yyyy-MM-dd";
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String yyyyMMdd = "yyyyMMdd";
	public static final String defaultPattern = "yyyy-MM-dd HH:mm:ss";
	
	public static Date pareseDate(Object value, String pattern, Date defaultValue) {
		if(value == null || StringUtils.isBlank(value.toString()))
			return defaultValue;
		
		if(value instanceof Date) {
			return (Date)value;
		}
		
		String formatPattern = defaultPattern;
		if(StringUtils.isNotEmpty(pattern)) {
			formatPattern = pattern;
		}
		
		return getDateByFormatString(value.toString(), formatPattern);
	}
	
	/**
	 * 获取date前lastDay的时间
	 * @param lastDay
	 * @return
	 */
	public static Date getLastDay(Date date,int lastDay){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -lastDay);
		return cal.getTime();
	}
	
	/**
	 * 
	 * @param date 当前日期
	 * @param Day  天数（如果day数为负数,说明是此日期前的天数）
	 * @return
	 */
	public static Date getBeforDay(Date date,int Day){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, Day);
		return cal.getTime();
	}
	
	
	public static Date getBeforeMonth(Date date, int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, month);
		return cal.getTime();
	}
	
	/**
	 * @param dateA
	 * @param dateB
	 * @return
	 */
	public static boolean isSameDay(Date dateA, Date dateB) {
		return org.apache.commons.lang.time.DateUtils.isSameDay(dateA, dateB);
	}
	
	/**
	 * 根据日期时间字符串转换成date对象
	 * @param formatString
	 * @return
	 */
	public static Date getDateByFormatString(String formatString,String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(formatString);
		} catch (ParseException e) {
			throw new DataParseException("日期为空或格式不正确", e);
		}
	}
	public static int subDay(Date minuend, Date subtrahend) {
		long diff  = minuend.getTime() - subtrahend.getTime();
		int diffDay = (int) (diff / 1000 / 60 / 60 / 24);
		return diffDay;
	}
	
	public static int dayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week = cal.get(Calendar.DAY_OF_WEEK);
		return week;
	}
	
	public static int year(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}
	
	public static Date getLastDayOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, 11);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		return cal.getTime();
	}
	
	public static String format(Date date) {
		return new SimpleDateFormat(defaultPattern).format(date);
	}
	public static String formatShort(Date date) {
		return new SimpleDateFormat(DATE_SHORT).format(date);
	}
	public static String formatToyyyyMMdd(Date date) {
		return new SimpleDateFormat(yyyyMMdd).format(date);
	}
	
	public static Date getDayBegin(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		return cal.getTime();
	}
	
	public static Date getDayEnd(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		
		return cal.getTime();
	}

	public static List<Date> getDayBeginAndEnd() {
		List<Date> list = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		
		Date start = cal.getTime();
		list.add(start);
		
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		list.add(cal.getTime());
		return list;
	}
	
	//获取今天的开始时间
	public static Date todayBeginTime(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		return cal.getTime();
		
	}
	/**
	 * 昨天开始时间
	 * @return
	 */
	public static Date yesterdaybeginTime(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -5);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE,00);
		cal.set(Calendar.SECOND, 00);
		return cal.getTime();
	
	}
	/**
	 * 昨天结束时间
	 * @return
	 */
	public static Date yesterdayEndTime(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE,59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
		
	}
	public static boolean isMatchStop(Date playingTime){
		if(playingTime!=null){
			if(playingTime.after(new Date())){
				return false;//未停售
			}else{
				return true;//停售
			}
		}
		return false;
	}

	/**
	 * 
	 * @return yyyy
	 */
	public static String getNowYear() {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return String.valueOf(cal.get(Calendar.YEAR));
	}
	
	public static Date converTime(String closeTime){
		if(StringUtils.isNotBlank(closeTime)){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			try {
				return sdf.parse(closeTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	public static String getWeekDayWithNumber(Integer num){
		String num2=String.valueOf(num);
		switch(num2){
		  case "1":return "周日";
		  case "2":return "周一";
		  case "3":return "周二";
		  case "4":return "周三";
		  case "5":return "周四";
		  case "6":return "周五";
		  case "7":return "周六";
		  default :return "";
		}
	}
	public static String getWeekDayWithTime(Date playingTime){
		if(playingTime!=null){
			Calendar cal=Calendar.getInstance();
			cal.setTime(playingTime);
			int day=cal.get(Calendar.DAY_OF_WEEK);
			switch(day){
			  case 1:return "周日";
			  case 2:return "周一";
			  case 3:return "周二";
			  case 4:return "周三";
			  case 5:return "周四";
			  case 6:return "周五";
			  case 7:return "周六";
			}
		}
		return "";
	}

	/**
	 * 获取d小时之前的时间
	 * @param date
	 * @param d
	 * @return
	 */
	public static Date getLastHoursTime(Date date, int d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, -d);
		return cal.getTime();
	}

	/**
	 * 计算两个时间的分钟差值
	 * @param maxDate
	 * @param minDate
	 * @return
	 */
	public static Long getMinuteOfTwoDate(Date maxDate, Date minDate) {
		long millSeconds = maxDate.getTime()-minDate.getTime();
		return millSeconds/1000/60;
	}

	/**
	 * 计算两个时间的秒差值
	 * @param maxDate
	 * @param minDate
	 * @return
	 */
	public static Long getSecondOfTwoDate(Date maxDate, Date minDate) {
		long millSeconds = maxDate.getTime()-minDate.getTime();
		return millSeconds/1000;
	}
	/**
	 * 
	 * 将开始日期和结束日期中间的年月分装成一个map,
	 * map的key是年份，value是月份的List集合
	 * @param earlyDate
	 * @param lastDate
	 * @return	
	 */
	public static Map<Integer,List<?>> splitDate(Date earlyDate,Date lastDate){
		Map<Integer,List<?>> map= new LinkedHashMap<Integer,List<?>>();
		Calendar cal= Calendar.getInstance();
		cal.setTime(earlyDate);
		int earlyYear=cal.get(Calendar.YEAR);
		int earlyMonth=cal.get(Calendar.MONTH)+1;

		cal.setTime(lastDate);
		int lastYear=cal.get(Calendar.YEAR);
		int lastMonth=cal.get(Calendar.MONTH)+1;

		List<Integer> monthList1=new ArrayList<Integer>();
		List<Integer> monthList2=new ArrayList<Integer>();
		List<Integer> monthList3=new ArrayList<Integer>();
		
		if(earlyYear==lastYear){
			for(int i=earlyMonth;i<=lastMonth;i++){
				monthList1.add(i);
			}
		}else{
			for(int i=earlyMonth;i<=12;i++){
				monthList1.add(i);
			}
			for(int i=1;i<=12;i++){
				monthList2.add(i);
			}
			for(int i=1;i<=lastMonth;i++){
				monthList3.add(i);
			}
		}	
			for(int i=lastYear;i>=earlyYear;i--){
				if(i==lastYear)
					map.put(i, monthList1);
				else if(i==earlyYear)
					map.put(i, monthList3);
				else
					map.put(i, monthList2);
			}

			
		return map;
	}

	/**
	 * 将时间参数转化为一个时间段
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date[] stringToDate(int year,int month){
		Date[] date=new Date[2];
		String early=null;
		String last=null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(month==year&&month==0){
			early="1970-01-01 00:00:00";			
			last=formatter.format(new Date()).toString();
		}
		else if(month>0){
			//TODO 这里会有一个bug
			early=""+year+"-"+month+"-01 00:00:00";
			last=""+year+"-"+month+"-31 23:59:59";
		}
		else{
			early=""+year+"-01-01 00:00:00";
			last=""+year+"-12-31 23:59:59";
		}
		try {
			date[0]=formatter.parse(early);
			date[1]=formatter.parse(last);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date getBeforWeek(Date date, int weeks) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.WEEK_OF_YEAR, weeks);
		return cal.getTime();
	}
	
	public static Date getBeforMonth(Date date, int months) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}
	/**
	 * 获得所在周第一天的开始时间
	 * @param time
	 * @return
	 */
	public static Date getWeekFirstDayBegin(Date time){
		Calendar cal = Calendar.getInstance();  
        cal.setTime(time);  
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        if (1 == dayWeek) {  
            cal.add(Calendar.DAY_OF_MONTH, -1);  
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值

        return getDayBegin(cal.getTime());
	}
	
	/**
	 * 获得所在周最后一天的结束时间
	 * @param time
	 * @return
	 */
	public static Date getWeekLastDayEnd(Date time){
		Calendar cal = Calendar.getInstance();  
        cal.setTime(time);  
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        if (1 == dayWeek) {  
            cal.add(Calendar.DAY_OF_MONTH, -1);  
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        
        int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        
        cal.add(Calendar.DATE, 6);
        
        return getDayEnd(cal.getTime());
	}
	
	/**
	 * 获得所在月第一天的开始时间
	 * @param time
	 * @return
	 */
	public static Date getMonthFirstDayBegin(Date date) {
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return getDayBegin(calendar.getTime());  
    }
	
	/**
	 * 获取所在年的第一天
	 * @param date
	 * @return
	 */
	public static Date getYearFirstDayBegin(Date date) {
		Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        
        return getDayBegin(calendar.getTime());
	}
	
	public static Date getYearLastDayEnd(Date date) {
		Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 1);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        
        return getDayEnd(calendar.getTime());
	}
	
	/**
	 * 获得所在月的最后一天的结束时间
	 * @param time
	 * @return
	 */
	public static Date getMonthLastDayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        
        return getDayEnd(calendar.getTime());  
    }

	/**
	 * 获得字符串日期的年月(yyyyMM)
	 * @param strDate
	 * @param fmt
	 * @return
	 */
	public static int getYYYYMM(String strDate, String fmt){
		Calendar calender = Calendar.getInstance();
		calender.setTime(getDateByFormatString(strDate, fmt));
		int yearYYYY = calender.get(Calendar.YEAR);
		int monthMM = calender.get(Calendar.MONTH) + 1;
		return Integer.valueOf(yearYYYY + (monthMM < 10?"0" + monthMM : monthMM + ""));
	}
	
	public static Date pareseDate2(Object value, String pattern, Date defaultValue) {
		if(value == null || StringUtils.isEmpty(value + ""))
			return defaultValue;
		
		if(value instanceof Date) {
			return (Date)value;
		}
		
		String formatPattern = defaultPattern;
		if(StringUtils.isNotEmpty(pattern)) {
			formatPattern = pattern;
		}
		
		try {
			return new SimpleDateFormat(formatPattern).parse(value.toString());
		} catch (ParseException e) {
			throw new DataParseException(e.getMessage(), e);
		}
	}
	
	public static Date parseDate(Object value, String pattern, Date defaultValue, String tips) {
		try {
			return pareseDate(value, pattern, defaultValue);
		} catch(DataParseException exp) {
			throw new ServiceException(tips, exp);
		}
	}
	
	
	public static Date getFirstDayOfWeek(Date date) {
		Calendar from = Calendar.getInstance();
		from.setTime(date);
		from = org.apache.commons.lang.time.DateUtils.truncate(from, Calendar.DATE);
		from.setFirstDayOfWeek(Calendar.MONDAY);
		from.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		return from.getTime();
	} 
	
	public static Date getLastDayOfWeek(Date date) {
		Calendar to = Calendar.getInstance();
		to.setTime(date);
		to = org.apache.commons.lang.time.DateUtils.truncate(to, Calendar.DATE);
		to.setFirstDayOfWeek(Calendar.MONDAY);
		to.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);		
	
		return to.getTime();
	}
	
	public static void main(String[] args) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String dateString = "2017-05-02";
		Date date = sdf.parse(dateString);

		Calendar cal = Calendar.getInstance();  
        cal.setTime(date);  
        
        System.out.println(cal.get(Calendar.WEEK_OF_MONTH));

        cal.setFirstDayOfWeek(Calendar.MONDAY);
        
        System.out.println(cal.get(Calendar.WEEK_OF_MONTH));
	}
}
