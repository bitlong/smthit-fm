/**
 * 
 */
package com.smthit.lang.exception;

/**
 * @author Bean
 *
 */
public class AssertException extends ServiceException {
	private static final long serialVersionUID = -162456259376571319L;

	/**
	 * @param msg
	 */
	public AssertException(String msg) {
		super(msg);
	}

	/**
	 * @param errorCode
	 * @param msg
	 */
	public AssertException(String errorCode, String msg) {
		super(errorCode, msg);
	}

	/**
	 * @param msg
	 * @param exception
	 */
	public AssertException(String msg, Throwable exception) {
		super(msg, exception);
	}

	/**
	 * @param errorCode
	 * @param msg
	 * @param exception
	 */
	public AssertException(String errorCode, String msg, Throwable exception) {
		super(errorCode, msg, exception);
	}

}
