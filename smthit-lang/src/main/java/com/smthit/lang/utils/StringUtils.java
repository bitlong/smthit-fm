/**
 * 
 */
package com.smthit.lang.utils;

/**
 * @author Bean
 *
 */
public class StringUtils {

	/**
	 * 
	 */
	public StringUtils() {
	}

	public static String parseString(Object value, String defaultValue) {
		if(value == null)
			return defaultValue;
		
		return value.toString();
	}
}
