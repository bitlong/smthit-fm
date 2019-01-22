/**
 * 
 */
package com.smthit.lang.dubbo;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Bean
 *
 */
@Data
@lombok.experimental.Accessors(chain = true)
public class ResponseBean<T> implements Serializable {
	private static final long serialVersionUID = -5883989358916182252L;

	public static final int OK = 0;
	public static final int ERROR = 500;

	// http 状态码
	private int code;

	// 返回信息
	private String msg;

	// 返回的数据
	private T data;

	public ResponseBean() {
	}

	public static <T> ResponseBean<T> newSuccess() {

		ResponseBean<T> rd = new ResponseBean<T>();
		rd.setCode(OK);
		rd.setMsg("OK");

		return rd;
	}

	public static <T> ResponseBean<T> newSuccess(String message) {

		ResponseBean<T> rd = new ResponseBean<T>();
		rd.setCode(OK);
		rd.setMsg(message);

		return rd;
	}

	public static <T> ResponseBean<T> newSuccess(String message, T data) {

		ResponseBean<T> rd = new ResponseBean<T>();
		rd.setCode(OK);
		rd.setMsg(message);
		rd.setData(data);

		return rd;
	}

	public static <T> ResponseBean<T> newSuccess(T data) {
		return ResponseBean.newSuccess("OK", data);
	}

	public static <T> ResponseBean<T> newFailed() {

		ResponseBean<T> rd = new ResponseBean<T>();
		rd.msg = "failed";
		rd.code = ERROR;
		return rd;

	}

	public static <T> ResponseBean<T> newFailed(String message) {

		ResponseBean<T> rd = new ResponseBean<T>();
		rd.msg = message;
		rd.code = ERROR;

		return rd;
	}

	public static <T> ResponseBean<T> newFailed(T data) {
		ResponseBean<T> rd = new ResponseBean<T>();
		rd.msg = "failed";
		rd.code = ERROR;
		rd.data = data;
		return rd;
	}

	public static <T> ResponseBean<T> newFailed(String message, T data) {
		ResponseBean<T> rd = new ResponseBean<T>();

		rd.msg = "failed";
		rd.code = ERROR;
		rd.data = data;

		return rd;
	}

	@SuppressWarnings({ "hiding", "unchecked" })
	public <T> ResponseBean<T> setCode(int code) {
		this.code = code;
		return (ResponseBean<T>) this;
	}

	@SuppressWarnings({ "hiding", "unchecked" })
	public <T> ResponseBean<T> setData(T data) {
		 ((ResponseBean<T>) this).data = data;
		return (ResponseBean<T>) this;
	}

	@SuppressWarnings({ "hiding", "unchecked" })
	public <T> ResponseBean<T> setMsg(String msg) {
		this.msg = msg;
		return (ResponseBean<T>) this;
	}
}
