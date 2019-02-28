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
		if (value == null)
			return defaultValue;

		return value.toString();
	}

	public static String separateCamelCase(String name, String separator) {
		StringBuilder translation = new StringBuilder();
		
		for (int i = 0; i < name.length(); i++) {
			char character = name.charAt(i);
			
			if (Character.isUpperCase(character) && translation.length() != 0) {
				translation.append(separator);
				translation.append(Character.toLowerCase(character));
			} else {
				translation.append(character);
			}
		}
		
		return translation.toString();
	}
}
