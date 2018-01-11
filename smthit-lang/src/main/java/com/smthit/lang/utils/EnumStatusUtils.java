/**
 * 
 */
package com.smthit.lang.utils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * @author Bean
 *
 */
public final class EnumStatusUtils {
	
	public static EnumStatus getStatusByValue(Class<?> enums, int value) {
		if(enums.isEnum()) {
			Object[] statuses = enums.getEnumConstants();
			for(int i = 0; i < statuses.length; i++) {
				EnumStatus status = (EnumStatus)statuses[i];
				if(status.getValue() == value) {
					return status;
				}
			}
		}
		
		return CommonEnumStatus.UNKOWN;
	}
	
	public static EnumStatus getStatusByDesc(Class<?> enums, String desc) {
		if(enums.isEnum()) {
			Object[] statuses = enums.getEnumConstants();
			for(int i = 0; i < statuses.length; i++) {
				EnumStatus status = (EnumStatus)statuses[i];
				if(status.getDesc().equals(desc)) {
					return status;
				}
			}
		}
		
		return CommonEnumStatus.UNKOWN;
	}
	
	/**
	 * 
	 * @param enums  枚举类
	 * @param sta    提示返回值
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Hashtable> getListByEnum(Class<?> enums,String sta){
		List<Hashtable> resList =new ArrayList<Hashtable>();
		Hashtable allTab = new Hashtable();
		allTab.put(sta+"_value", "-1");  
		allTab.put(sta+"_name", "全部");
		resList.add(0,allTab);
		if(enums.isEnum()) {
			Object[] statuses = enums.getEnumConstants();
			for(int i = 0; i < statuses.length; i++) {
				EnumStatus status = (EnumStatus)statuses[i];
				Hashtable tab = new Hashtable();
				tab.put(sta+"_value", status.getValue());  
				tab.put(sta+"_name", status.getDesc());
				resList.add((i+1),tab);
			}
		}
		return resList;
	}
	
	/**
	 * 数据列表
	 * @param enums  枚举类
	 * @param sta    提示返回值
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Hashtable> getDataListByEnum(Class<?> enums,String sta){
		List<Hashtable> resList =new ArrayList<Hashtable>();
		if(enums.isEnum()) {
			Object[] statuses = enums.getEnumConstants();
			for(int i = 0; i < statuses.length; i++) {
				EnumStatus status = (EnumStatus)statuses[i];
				Hashtable tab = new Hashtable();
				tab.put(sta+"_value", status.getValue());  
				tab.put(sta+"_name", status.getDesc());
				resList.add((i),tab);
			}
		}
		return resList;
	}
}
