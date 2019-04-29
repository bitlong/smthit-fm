/**
 * 
 */
package com.smthit.framework.dal.data;

/**
 * @author Bean
 *
 */
public enum EnumOrder {
	ASC("asc", "升序"),
	DESC("desc", "降序");
	
	private String value;
	private String desc;
	
	private EnumOrder(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}
	
	public String getDesc() {
		return desc;
	}
 }
