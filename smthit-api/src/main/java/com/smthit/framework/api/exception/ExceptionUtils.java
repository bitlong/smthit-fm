/**
 * 
 */
package com.smthit.framework.api.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

public class ExceptionUtils {
	public static String getFormatExceptionMessage(Exception e) {
		List<String> exceptionStack = new ArrayList<String>();
		exceptionStack.add(e.getClass().getName());
		
		for (StackTraceElement ste : e.getStackTrace()) {
			exceptionStack.add(" at " + ste.getClassName() + "."
					+ ste.getMethodName() + "(" + ste.getFileName() + ":"
					+ ste.getLineNumber() + ")");
		}
		
		return StringUtils.collectionToDelimitedString(exceptionStack, "");
	}
}
