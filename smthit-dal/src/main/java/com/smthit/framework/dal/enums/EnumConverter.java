/**
 * 
 */
package com.smthit.framework.dal.enums;

import javax.persistence.AttributeConverter;

import com.smthit.lang.utils.EnumStatus;

/**
 * @author Bean
 *
 */
public abstract class EnumConverter<T> implements AttributeConverter<T, Integer> {

	/**
	 * 
	 */
	public EnumConverter() {
	}

	@Override
	public Integer convertToDatabaseColumn(T value) {
		if(value == null) {
			return null;
		}
		
		EnumStatus es = (EnumStatus)value;
		
		return es.getValue();
	}

	@Override
	public T convertToEntityAttribute(Integer value) {
		if(value == null) {
			return null;
		}
		
		return  (T)getEnumStatus(value);		
	}

	public abstract T getEnumStatus(Integer value);
}
