/**
 * 
 */
package com.smthit.framework.api.protocol;

import com.google.gson.annotations.Expose;

/**
 * @author Bean
 * @since 1.0.0
 */
public class Request {
	@Expose(serialize = false, deserialize = false)
	private RequestHeader header;
	
	private byte[] body;
	
	public Request() {
		header = new RequestHeader();
	}
	
	public RequestHeader getHeader() {
		return header;
	}
	
	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}
}
