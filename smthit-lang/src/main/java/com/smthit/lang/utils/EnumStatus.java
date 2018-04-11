package com.smthit.lang.utils;

import com.smthit.lang.exception.ServiceException;

public interface EnumStatus {
	public int getValue();
	public String getDesc();
	
	default public EnumStatus getEnumStatus(int value) {
		throw new ServiceException("枚举方法未实现-not implement this method.");
	}
	
	default public EnumStatus getEnumStatus(Class<?> typClass, Integer value) {
		try {
			return EnumStatusUtils.getStatusByValue(typClass, value); 
		} catch(Exception exp) {
			return CommonEnumStatus.UNKOWN;
		}
	}

}
