/**
 * 
 */
package com.smthit.task.engine;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Bean
 *
 */
@Slf4j
public class TaskExecutorDefine<T extends TaskExecutor> {
	@Getter
	private Set<String> taskKeys = new HashSet<>(10);
	
	@Getter
	private Class<T> taskExecutorCls;
	
	public TaskExecutorDefine(String taskKey, Class<T> cls) {
		this.taskExecutorCls = cls;
		this.taskKeys.add(taskKey);
	}
	
	public T createTaskExecutor() {
		try {
			return taskExecutorCls.newInstance();	
		} catch(Exception exp) {
			log.error(exp.getMessage(), exp);
			throw new TaskException("创建任务执行器失败, 原因: " + exp.getMessage());
		}
		
	}
}
