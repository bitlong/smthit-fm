/**
 * 
 */
package com.smthit.lang.utils;

import org.apache.commons.lang.StringUtils;

import com.smthit.lang.exception.DataParseException;
import com.smthit.lang.exception.ServiceException;


/**
 * @author Bean
 *
 */
public class IntegerUtils {

	/**
	 * 
	 */
	public IntegerUtils() {
	}

	public static Integer parseInteger(Object value, Integer defaultValue) {
		if(value == null || StringUtils.isBlank(value.toString())) {
			return defaultValue;
		}
		
		if(value instanceof Integer) {
			return (Integer)value;
		}
		
		if(value.getClass().isAssignableFrom(Number.class)) {
			return ((Number)value).intValue();
		}
		
		try {
			return Integer.parseInt(value.toString());
		} catch(Exception exp) {
			throw new DataParseException("数据类型转换失败, value = " + value);
		}		
	}
	
	public static Integer parseInteger(Object value, Integer defaultValue, String tips) {
		try {
			return parseInteger(value, defaultValue);
		} catch(DataParseException exp) {
			throw new ServiceException(tips, exp);
		}
	}
}
