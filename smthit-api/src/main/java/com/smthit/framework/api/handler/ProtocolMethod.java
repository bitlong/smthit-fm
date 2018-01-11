/**
 * 
 */
package com.smthit.framework.api.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.google.common.collect.Range;
import com.smthit.framework.api.protocol.Request;
import com.smthit.framework.api.protocol.Response;

/**
 * @author Bean
 * @since 1.0.0
 */
public class ProtocolMethod {
	private static Logger logger = Logger.getLogger(ProtocolMethod.class);
	
	private AbstractHandler handler;
	private Range<Double> version;
	private Method method;
	private boolean requireLogin;

	public Range<Double> getVersion() {
		return version;
	}

	public void setVersion(Range<Double> version) {
		this.version = version;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public AbstractHandler getHandler() {
		return handler;
	}

	public void setHandler(AbstractHandler handler) {
		this.handler = handler;
	}

	/**
	 * 
	 * @param request
	 * @return
	 * 
	 * @since 1.0.0
	 */
	public Response invoke(Request request) throws Throwable {
		try {
			return (Response)method.invoke(handler, request);
		}catch(InvocationTargetException exp) {
			throw exp.getTargetException();
		}
	}

	public boolean isRequireLogin() {
		return requireLogin;
	}

	public void setRequireLogin(boolean requireLogin) {
		this.requireLogin = requireLogin;
	}
}
