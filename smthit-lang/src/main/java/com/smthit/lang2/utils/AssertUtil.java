/**
 * 
 */
package com.smthit.lang2.utils;

import java.util.Objects;

import org.apache.commons.lang.StringUtils;

import com.smthit.lang.exception.AssertException;
import com.smthit.lang.utils.EnumStatus;
import com.smthit.lang.utils.EnumStatusUtils;
import com.smthit.lang.utils.RegexUtils;

public class AssertUtil {
	
	public static void assertNotNull(String value, String tips) {
		if (Objects.isNull(value)) {
			throw new AssertException(StringUtils.isEmpty(tips) ? "验证失败, 字符串不能为空" : tips);
		}
	}
	
	public static void assertNotNull(String value) {
		assertNotNull(value, null);
	}

	public static void assertNotEmpty(String value, String tips) {
		if (StringUtils.isEmpty(value)) {
			throw new AssertException(StringUtils.isEmpty(tips) ? "验证失败, 字符串不能为空" : tips);
		}
	}
	
	public static void assertNotEmpty(String value) {
		assertNotEmpty(value, null);
	}

	public static void assertTel(String value, String tips) {
		assertNotNull(value, tips);
		if (!RegexUtils.isTel(value)) {
			throw new AssertException(StringUtils.isEmpty(tips) ? "验证失败, 不是一个有效的手机/电话号码" : tips);
		}
	}
	
	public static void assertTel(String value) {
		assertTel(value, null);
	}

	public static void assertValidateEmail(String value, String tips) {
		assertNotNull(value, tips);
		if (!RegexUtils.isEmail(value)) {
			throw new AssertException(StringUtils.isEmpty(tips) ? "验证失败, 不是一个有效的邮箱地址" : tips);
		}
	}
	
	public static void assertValidateEmail(String value) {
		assertValidateEmail(value, null);
	}

	public static void assertMobile(String value, String tips) {
		if (!RegexUtils.isMobile(value)) {
			throw new AssertException(StringUtils.isEmpty(tips) ? "验证失败, 不是一个有效的手机号" : tips);
		}
	}
	
	public static void assertMobile(String value) {
		assertMobile(value, null);
	}

	public static void assertGT0(Number value, String tips) {
		if (value == null || value.longValue() <= 0) {
			throw new AssertException(StringUtils.isEmpty(tips) ? "验证失败, 必须大于0" : tips);
		}
	}
	
	public static void assertGT0(Number value) {
		assertGT0(value, null);
	}

	public static void assertGE0(Number value, String tips) {
		//TODO 这里能有问题
		if (value == null || value.doubleValue() < 0) {
			throw new AssertException(StringUtils.isEmpty(tips) ? "验证失败, 必须大于等于0" : tips);
		}
	}

	public static void assertNotNull(Object obj, String tips) {
		if (Objects.isNull(obj)) {
			throw new AssertException(StringUtils.isEmpty(tips) ? "验证失败, 对象不存在" : tips);
		}
	}

	public static void assertNotNull(Object obj) {
		assertNotNull(obj,  null);
	}
	
	public static void assertEnumExist(int status, Class<? extends EnumStatus> cls, String tips) {
		EnumStatus value = EnumStatusUtils.getStatusByValue(cls, status);
		if (Objects.isNull(value)) {
			throw new AssertException(StringUtils.isEmpty(tips) ? "验证失败, 枚举类型不存在" : tips);
		}
	}
	
	public static void assertEnumExist(int status, Class<? extends EnumStatus> cls) {
		assertEnumExist(status, cls);
	}

	public static void assertEqual(Object val, Object val2, String tips) {
		if (val == null || val2 == null) {
			throw new AssertException(StringUtils.isEmpty(tips) ? "验证失败, 两个值不相等" : tips);
		}
		
		if (val.equals(val2)) {
			return;
		} else {
			throw new AssertException(StringUtils.isEmpty(tips) ? "验证失败, 两个值不相等" : tips);
		}
	}
	
	public static void assertEqual(Object val, Object val2) {
		assertEqual(val, val2);
	}

	public static void assertScope(Number start, Number end, Number value, String tips) {
		//TODO 这里有问题，需要优化
		if (value == null || value.doubleValue() < start.doubleValue() || value.doubleValue() > end.doubleValue()) {
			throw new AssertException(StringUtils.isEmpty(tips) ? "验证失败, 数值不再范围内" : tips);
		}
	}
	
	public static void assertScope(Number start, Number end, Number value) {
		assertScope(start, end, value);
	}
	
	public static void assertTrue(Boolean value, String tips) {
		assertNotNull(value);
		
		if(value == false) {
			throw new  AssertException(StringUtils.isEmpty(tips) ? "验证失败, 比如为True" : tips);
		}
		
	}
	
	public static void assertTrue(Boolean value) {
		assertTrue(value, null);
	}
}