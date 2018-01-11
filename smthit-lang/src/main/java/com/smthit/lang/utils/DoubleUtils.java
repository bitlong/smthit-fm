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
public class DoubleUtils {

	/**
	 * 
	 */
	public DoubleUtils() {
	}

	public static Double parseDouble(Object value, Double defaultValue) {
		if(value == null || StringUtils.isBlank(value.toString())) {
			return defaultValue;
		}
		
		if(value instanceof Double) {
			return (Double)value;
		}
		
		if(value.getClass().isAssignableFrom(Number.class)) {
			return ((Number)value).doubleValue();
		}
		
		try {
			return Double.parseDouble(value.toString());
		}catch(NumberFormatException exp) {
			throw new DataParseException(exp.getMessage(), exp);
		}
	}
	
	public static Double parseDouble(Object value, Double defaultValue, String tips) {
		try {
			return parseDouble(value, defaultValue);
		} catch(DataParseException exp) {
			throw new ServiceException(tips, exp);
		}
	}
}
