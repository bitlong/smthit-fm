/**
 * 
 */
package com.smthit.lang.data;

import lombok.Data;

/**
 * @author Bean Layui分页返回数据的默认格式
 */
@Data
@lombok.experimental.Accessors(chain = true)
public class PageData {
	private int code;
	private String msg;
	private long count;
	private Object data;

	public static PageData newFailed() {
		PageData pageData = new PageData();
		pageData.setCode(500);
		pageData.setMsg("Failed");
		
		return pageData;
	}
	
	public static PageData newSuccess() {
		PageData pageData = new PageData();
		
		pageData.setCode(0);
		pageData.setMsg("Success");
		
		return pageData;
	}
	
	public PageData() {
	}

	public PageData(int code, String msg, long count, Object data) {
		this.code = code;
		this.msg = msg;
		this.count = count;
		this.data = data;
	}
}
