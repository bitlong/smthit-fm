/**
 * 
 */
package com.smthit.lang.exception;

/**
 * @author Bean
 *
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -5711076470957563907L;

	private String errorCode;

	public ServiceException(String msg) {
		super(msg);
	}

	public ServiceException(String errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}

	public ServiceException(String msg, Throwable exception) {
		super(msg, exception);
	}

	public ServiceException(String errorCode, String msg, Throwable exception) {
		super(msg, exception);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
