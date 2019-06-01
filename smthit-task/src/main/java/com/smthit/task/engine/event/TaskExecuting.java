/**
 * 
 */
package com.smthit.task.engine.event;

import com.smthit.task.engine.Task;
import com.smthit.task.engine.TaskContext;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Bean
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TaskExecuting extends TaskEvent {
	/**
	 * 当前执行步长
	 */
	private Integer currentStep;

	public TaskExecuting(TaskContext taskContext, Task task) {
		this.task = task;
		this.taskContext = taskContext;
	}

}
