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
	
	public OrderBy(String field, EnumOrder order) {
		this.field = field;
		this.order = order;
	}
}
