/**
 * 
 */
package com.smthit.lang.utils;

import org.apache.commons.lang.StringUtils;

import com.smthit.lang.exception.ServiceException;

/**
 * @author Bean
 *
 */
@Deprecated
public class AssertUtils {
	public static void assertNotNull(String value, String tips) {
		if(StringUtils.isEmpty(value)) {
			throw new ServiceException(StringUtils.isEmpty(tips) ? "字符串不能为空" : tips);
		}
	}
	
	public static void assertValidateTel(String value,String tips){
		assertNotNull(value, tips);
		if(!RegexUtils.isTel(value)){
			throw new ServiceException(StringUtils.isEmpty(tips) ? "不是一个有效的手机/电话号码" : tips);
		}
	}
	
	public static void assertValidateEmial(String value,String tips){
		assertNotNull(value, tips);
		if(!RegexUtils.isEmail(value)){
			throw new ServiceException(StringUtils.isEmpty(tips) ? "不是一个有效的邮箱地址" : tips);
		}
	}
	
	public static void assertMobile(String value, String tips) {
		if(!RegexUtils.isMobile(value)) {
			throw new ServiceException(StringUtils.isEmpty(tips) ? "不是一个有效的手机号" : tips);		
		}
	}
	
	public static void assertGreatThanZero(Number value, String tips) {
		if(value == null || value.longValue() <= 0) {
			throw new ServiceException(StringUtils.isEmpty(tips) ? "必须大于0" : tips);	
		}
	}
	
	public static void assertGreatThanOrEqualZero(Number value, String tips) {
		if(value == null || value.longValue() < 0) {
			throw new ServiceException(StringUtils.isEmpty(tips) ? "必须大于等于0" : tips);	
		}
	}
	
	public static void assertObjectExist(Object obj, String tips) {
		if(obj == null) {
			throw new ServiceException(StringUtils.isEmpty(tips) ? "对象不存在" : tips);
		}
	}
	
	public static void assertEnumExist(int status, Class<?> cls, String tips) {
		EnumStatus value = EnumStatusUtils.getStatusByValue(cls, status);
		if(value == null) {
			throw new ServiceException(StringUtils.isEmpty(tips) ? "枚举类型不存在" : tips);
		}
	}
	
	public static void assertEqual(Object val, Object val2, String tips) {
		if(val == null || val2 == null)
			throw new ServiceException(StringUtils.isEmpty(tips) ? "两个值不相等" : tips);
		
		if(val.equals(val2)) {
			return;
		} else {
			throw new ServiceException(StringUtils.isEmpty(tips) ? "两个值不相等" : tips);
		}
	}
	
	public static void assertScope(Number start, Number end, Number value, String tips) {
		if(value == null || value.longValue() < start.longValue() || value.longValue() > end.longValue()) {
			throw new ServiceException(StringUtils.isEmpty(tips) ? "数值不再范围内" : tips);
		}
	}
}
