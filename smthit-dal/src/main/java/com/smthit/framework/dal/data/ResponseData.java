/**
 * 
 */
package com.smthit.framework.dal.data;

import java.util.List;
import java.util.Map;

/**
 * @author Bean
 *
 */
public class ResponseData {
	public static final int SUCCESS = 200;
	public static final int DEFAULT_ERROR = 500;
	
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
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public Map<String, String> getFieldErrors() {
		return fieldErrors;
	}
	public void setFieldErrors(Map<String, String> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}	
}
