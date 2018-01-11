/**
 * 
 */
package com.smthit.framework.api.exception;

/**
 * @author Bean
 *
 */
public class ApiException extends RuntimeException {
	private static final long serialVersionUID = -5711076470957563907L;

	private String errorCode;

	public ApiException(String msg) {
		super(msg);
	}

	public ApiException(String errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}

	public ApiException(String msg, Throwable exception) {
		super(msg, exception);
	}

	public ApiException(String errorCode, String msg, Throwable exception) {
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
