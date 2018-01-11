/**
 * 
 */
package com.smthit.framework.api.protocol;

import com.google.gson.annotations.Since;

/**
 * @author Bean
 * @since 1.0.0
 */
public class DefaultErrorResponse extends Response {
	@Since(1.0)
	private String errorMsg;
	
	public DefaultErrorResponse(int status, String msg, String exception) {
		setStatus(status);
		setMemo(msg);
		
		setErrorMsg(exception);
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
