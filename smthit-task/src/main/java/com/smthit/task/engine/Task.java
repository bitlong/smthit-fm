/**
 * 
 */
package com.smthit.task.engine;

import com.smthit.task.engine.enums.EnumTaskType;

import lombok.Data;

/**
 * @author Bean
 *
 */
@Data
public class Task {
	private String name;
	private String taskKey;
	private String taskNo;
	private Long userId;
	private EnumTaskType taskType;
	/**总步长*/
	private Integer totalStep;
	/**当前步长*/
	private Integer currentStep;
	
	public Task() {
	}

}
