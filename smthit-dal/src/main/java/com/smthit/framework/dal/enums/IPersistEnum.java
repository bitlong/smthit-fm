/**
 * 
 */
package com.smthit.framework.dal.enums;

import java.util.Map;

/**
 * @author Bean
 *
 */
public interface IPersistEnum<E extends Enum<?>> {
	/**
	 * 获取被持久化字段的值
	 * 
	 * @return 被持久化字段的值
	 */
	String getPersistedValue();

	/**
	 * 由被持久化的字段的值获取枚举类型
	 * 
	 * @param persistedValue
	 * @return
	 */
	E returnEnum(String persistedValue);

	/**
	 * 获取枚举的所有枚举项
	 * 
	 * @return map
	 */
	Map<String, E> getAllValueMap();

}
