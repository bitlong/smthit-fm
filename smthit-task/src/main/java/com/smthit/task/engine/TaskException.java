/**
 * 
 */
package com.smthit.task.engine;

import com.smthit.lang.exception.ServiceException;

/**
 * @author Bean
 *
 */
public class TaskException extends ServiceException {
	private static final long serialVersionUID = 6210258907169307773L;

	/**
	 * @param msg
	 */
	public TaskException(String msg) {
		super(msg);
	}

	/**
	 * @param errorCode
	 * @param msg
	 */
	public TaskException(String errorCode, String msg) {
		super(errorCode, msg);
	}

	/**
	 * @param msg
	 * @param exception
	 */
	public TaskException(String msg, Throwable exception) {
		super(msg, exception);
	}

	/**
	 * @param errorCode
	 * @param msg
	 * @param exception
	 */
	public TaskException(String errorCode, String msg, Throwable exception) {
		super(errorCode, msg, exception);
	}

}
