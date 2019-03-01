/**
 * 
 */
package com.smthit.lang.exception;

/**
 * @author Bean
 *
 */
public class DataParseException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6466423297340285300L;

	/**
	 * @param msg
	 */
	public DataParseException(String msg) {
		super(msg);
	}

	/**
	 * @param errorCode
	 * @param msg
	 */
	public DataParseException(String errorCode, String msg) {
		super(errorCode, msg);
	}

	/**
	 * @param msg
	 * @param exception
	 */
	public DataParseException(String msg, Throwable exception) {
		super(msg, exception);
	}

	/**
	 * @param errorCode
	 * @param msg
	 * @param exception
	 */
	public DataParseException(String errorCode, String msg, Throwable exception) {
		super(errorCode, msg, exception);
	}

}
