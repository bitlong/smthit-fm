/**
 * 
 */
package com.smthit.task.engine.event;

import java.util.Calendar;
import java.util.Date;

import com.smthit.task.engine.Task;
import com.smthit.task.engine.TaskContext;

import lombok.Data;

/**
 * @author Bean
 *
 */
@Data
public class TaskEvent {
	/**
	 * 任务的KEY
	 */
	private String key;
	
	/**
	 * 任务名称
	 */
	private String name;
	
	
	/**
	 * 任务总步长
	 */
	private Integer totalStep;
	
	/**
	 * 任务上下文
	 */
	protected TaskContext taskContext;
	
	/**
	 * 任务开始时间
	 */
	private Date startTime;
	
	/**
	 * 任务结束时间
	 */
	private Date endTime;
	
	/**
	 * 当前执行的任务
	 */
	protected Task task;
	
	/**
	 * 任务的执行时长
	 * @return
	 */
	public Integer getDuration() {
		if(startTime == null) {
			return 0;
		}
		
		if(endTime == null) {
			return (int)(Calendar.getInstance().getTime().getTime() - startTime.getTime()) / 1000;
		}
		
		return (int)(endTime.getTime() - startTime.getTime()) / 1000;
	}
}
