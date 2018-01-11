/**
 * 
 */
package com.smthit.framework.api.protocol;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;

/**
 * @author Bean
 * @since 1.0.0
 */
public class Response {
	/**
	 * 协议头
	 * @since 1.0.0
	 */
	@Expose(serialize = false)
	private ResponseHeader header;
	
	/**
	 * 返回值状态
	 * @since 1.0.0
	 */
	@Since(value = 1.0)
	private int status;
	
	/**
	 * 返回值状态描述
	 * @since 1.0.0
	 */
	@Since(value = 1.0)
	private String memo;
	
	private static Response success = new Response(200, "success");
	
	private static Response failed = new Response(500, "Failed.");
	
	public static Response newSuccess() {
		return success;
	}
	
	public static Response newFailed() {
		return failed;
	}
	
	public Response() {
	}
	
	public Response(int status, String desc) {
		this.status = status;
		this.memo = desc;
	}
	
	public ResponseHeader getHeader() {
		return header;
	}

	public void setHeader(ResponseHeader header) {
		this.header = header;
	}

	public int getStatus() {
		return status;
	}

	public Response setStatus(int status) {
		this.status = status;
		return this;
	}

	public String getMemo() {
		return memo;
	}

	public Response setMemo(String memo) {
		this.memo = memo;
		return this;
	}
}
