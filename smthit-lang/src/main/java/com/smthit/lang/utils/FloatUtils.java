/**
 * 
 */
package com.smthit.lang.utils;

import com.smthit.lang.exception.DataParseException;
import com.smthit.lang.exception.ServiceException;

/**
 * @author Bean
 *
 */
public class FloatUtils {

	/**
	 * 
	 */
	public FloatUtils() {
	}
	
	public static Float parseFloat(Object value, Float defaultValue) {
		if(value == null) {
			return defaultValue;
		}
		
		if(value instanceof Float) {
			return (Float)value;
		}
		
		if(value.getClass().isAssignableFrom(Number.class)) {
			return ((Number)value).floatValue();
		}
		
		try {
			return Float.parseFloat(value.toString());
		}catch(NumberFormatException exp) {
			throw new DataParseException(exp.getMessage(), exp);
		}
	}
	
	public static Float parseFloat(Object value, Float defaultValue, String tips) {
		try {
			return parseFloat(value, defaultValue);
		} catch(DataParseException exp) {
			throw new ServiceException(tips, exp);
		}
	}
}
