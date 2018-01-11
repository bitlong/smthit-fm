/**
 * 
 */
package com.smthit.lang.utils;

/**
 * @author Bean
 *
 */
public enum CommonEnumStatus implements EnumStatus {
	UNKOWN(-10000, "未知状态");

	private int value;
	private String desc;

	private CommonEnumStatus(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}
	
	@Override
	public int getValue() {
		return value;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	@Override
	public EnumStatus getEnumStatus(int value) {
		return EnumStatusUtils.getStatusByValue(CommonEnumStatus.class, value);
	}
}
