/**
 * 
 */
package com.smthit.lang.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Bean
 *
 */
public class ReflectUtils {
	public static Field[] getAllField(Class<?> clazz) {
		
		ArrayList<Field> fieldList = new ArrayList<Field>();
		
		Field[] dFields = clazz.getDeclaredFields();
		
		if (null != dFields && dFields.length > 0) {
			fieldList.addAll(Arrays.asList(dFields));
		}

		Class<?> superClass = clazz.getSuperclass();
		if (superClass != Object.class) {
			Field[] superFields = getAllField(superClass);
			if (null != superFields && superFields.length > 0) {
				for (Field field : superFields) {
					if (!isContain(fieldList, field)) {
						fieldList.add(field);
					}
				}
			}
		}
		
		Field[] result = new Field[fieldList.size()];
		fieldList.toArray(result);
		
		return result;
	}

	private static boolean isContain(ArrayList<Field> fieldList, Field field) {
		for (Field temp : fieldList) {
			if (temp.getName().equals(field.getName())) {
				return true;
			}
		}
		return false;
	}

	public static Field getFields(Class<?> clazz, String attribute) {
		Field[] dFields = clazz.getDeclaredFields();

		for(Field field : dFields) {
			if(field.getName().equals(attribute)) {
				return field;
			}
		}
		
		Class<?> superClass = clazz.getSuperclass();
		if (superClass != Object.class) {
			return getFields(superClass, attribute);
		}
		
		return null;
	}

}
