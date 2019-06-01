package com.smthit.task.engine.event;

import com.smthit.task.engine.Task;
import com.smthit.task.engine.TaskContext;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务中断事件
 * 
 * @author Bean
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TaskTerminalEvent extends TaskEvent {
	
	public TaskTerminalEvent(TaskContext taskContext, Task task) {
		this.task = task;
		this.taskContext = taskContext;
	}

}
