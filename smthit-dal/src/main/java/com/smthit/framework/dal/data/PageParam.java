/**
 * 
 */
package com.smthit.framework.dal.data;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Bean
 *
 */
@Data
@NoArgsConstructor
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
}
