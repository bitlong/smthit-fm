/**
 * 
 */
package com.smthit.lang.logger;

import com.smthit.lang.exception.ServiceException;

/**
 * @author Bean
 *
 */
public class LogException extends ServiceException {
	private static final long serialVersionUID = -6845488210415576269L;

	/**
	 * @param msg
	 */
	public LogException(String msg) {
		super(msg);
	}

	/**
	 * @param errorCode
	 * @param msg
	 */
	public LogException(String errorCode, String msg) {
		super(errorCode, msg);
	}

	/**
	 * @param msg
	 * @param exception
	 */
	public LogException(String msg, Throwable exception) {
		super(msg, exception);
	}

	/**
	 * @param errorCode
	 * @param msg
	 * @param exception
	 */
	public LogException(String errorCode, String msg, Throwable exception) {
		super(errorCode, msg, exception);
	}

}
