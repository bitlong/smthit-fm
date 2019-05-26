/**
 * 
 */
package com.smthit.lang.data;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.ToString;

/**
 * @author Bean
 *
 */
@Data
@ToString
@lombok.experimental.Accessors(chain = true)
public class ResponseData {
	public static final int SUCCESS = 200;
	public static final int DEFAULT_ERROR = 500;
	
	public static ResponseData newSuccess() {
		ResponseData rd = new ResponseData(true, null);
		rd.setStatus(SUCCESS);
		rd.setMessage("Success");
		
		return rd;
	}
	
	public static ResponseData newSuccess(String message) {
		ResponseData rd = new ResponseData(true, null);
		rd.setStatus(SUCCESS);
		rd.setMessage(message);
		
		return rd;
	}
	
	
	public static ResponseData newSuccess(Object data) {
		return ResponseData.newSuccess("", data);
	}
	
	public static ResponseData newSuccess(String message, Object data) {
		ResponseData rd = new ResponseData(true, data);
		rd.setStatus(SUCCESS);
		rd.setMessage(message);
		
		return rd;
	}
	
	public static ResponseData newFailed() {
		ResponseData rd = new ResponseData(false, null);
		rd.message = "Failed";
		rd.data = "Failed";
		rd.setStatus(DEFAULT_ERROR);
		return rd;
	}
	
	public static ResponseData newFailed(String message) {
		ResponseData rd = new ResponseData(false, null);
		rd.message = message;
		rd.data = message;
		rd.setStatus(DEFAULT_ERROR);
		return rd;
	}
	
	public static ResponseData newFailed(Object data) {
		return ResponseData.newFailed("", data);
	}
	
	public static ResponseData newFailed(String message, Object data) {
		ResponseData rd = new ResponseData(false, data);
		rd.message = message;
		rd.data = data;
		rd.setStatus(DEFAULT_ERROR);
		return rd;
	}
	
	private boolean success;
	private Object data;
	private String message;
	private int status;
	
	private List<String> errors;
	private Map<String, String> fieldErrors;
	
	public ResponseData(boolean success, Object data) {
		this.success = success;
		this.data = data;
	}
}
