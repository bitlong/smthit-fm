/**
 * 
 */
package com.smthit.framework.dal.data;

import lombok.Data;

/**
 * @author Bean
 *
 */
@Data
public class OrderBy {
	private String field;
	private EnumOrder order;
	
	public OrderBy(String fieldName, EnumOrder order) {
		this.field = fieldName;
		this.order = order;
	}
}
