/**
 * 
 */
package com.smthit.task.data;

import lombok.Data;

/**
 * @author Bean
 *
 */
@Data
public class Task {
	private Long id;
	private String name;
	private String key;
	
	public Task() {
	}

}
