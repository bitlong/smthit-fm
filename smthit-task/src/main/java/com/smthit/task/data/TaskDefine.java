/**
 * 
 */
package com.smthit.task.data;

import com.smthit.task.engine.TaskExecutor;

import lombok.Data;

/**
 * @author Bean
 * 任务的定义
 * 包含：
 * 	任务的基本信息
 * 	任务的执行器
 */
@Data
public class TaskDefine {
	private TaskExecutor taskExecutor;
	private Task task;

	public TaskDefine() {
	}
}
