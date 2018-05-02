/**
 * 
 */
package com.smthit.framework.dal.data;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * @author Bean
 *
 */
@Data
@lombok.experimental.Accessors(chain = true)
public class PageParam {
	private int pageNumber;
	private int pageSize;
	private Map<String, Object> params;
	private String orderBy;

	public PageParam(int pageNumber, int pageSize, Map<String, Object> params) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.params = params;
	}
	
	public PageParam() {
		this.pageNumber = 1;
		this.pageSize = 100;
		this.params = new HashMap<String, Object>();
		this.orderBy = " id desc";
	}
}
