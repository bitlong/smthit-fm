/**
 * 
 */
package com.smthit.lang2.utils;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

import com.smthit.lang.exception.ServiceException;

/**
 * @author Bean
 *
 */
public final class ClassKit {

	private ClassKit() {
	}

	/**
	 * 获取对象属性名称
	 * 
	 * @return
	 */
	public static final <T, R> String getFieldName(Property<T, R> property) {
		try {
			Method method = property.getClass().getDeclaredMethod("writeReplace");
			method.setAccessible(Boolean.TRUE);
			SerializedLambda serializedLambda = (SerializedLambda) method.invoke(property);
			String methodName = serializedLambda.getImplMethodName();

			String propertyName = null;

			if (methodName.startsWith("get")) {
				propertyName = methodName.substring(3);
			} else {
				// 例如: isAdmin, 返回admin
				propertyName = methodName.substring(2);
			}

			return StringUtil.toLowerCaseFirstOne(propertyName);
		} catch (Exception exp) {
			throw new ServiceException("获取属性失败, 原因:" + exp.getMessage(), exp);
		}
	}
}
