/**
 * 
 */
package com.smthit.framework.api.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Since;

/**
 * @author Bean
 * @since 1.0.0
 */
public class ReflectMethodUtils {
	/**
	 * 
	 * @param cls
	 * @param includeParent
	 * @return
	 * 
	 * @since 1.0.0
	 */
	public static List<Field> getClassFields(Class<?> cls, boolean includeParent) {
		List<Field> fields = new ArrayList<Field>();
		
		Field[] fs = cls.getDeclaredFields();
		for(Field f : fs) {
			if(f.isAnnotationPresent(Since.class)) {
				fields.add(f);
			}
		}
		
		if(includeParent) {
			getParentClassFields(fields, cls.getSuperclass());
		}
		
		return fields;
	}
	
	/**
	 * 
	 * @param fields
	 * @param cls
	 * @return
	 * 
	 * @since 1.0.0
	 */
	private static List<Field> getParentClassFields(List<Field> fields, Class<?> cls) {
		if(cls == null || cls.getDeclaredFields() == null) {
			return fields;
		}
		
		Field[] fs = cls.getDeclaredFields();
		
		for(Field f : fs) {
			if(f.isAnnotationPresent(Since.class)) {
				fields.add(f);
			}
		}
		
		if(cls.getSuperclass() == null) {
			return fields;
		} else {
			return getParentClassFields(fields, cls.getSuperclass());
		}
		
	}
}
