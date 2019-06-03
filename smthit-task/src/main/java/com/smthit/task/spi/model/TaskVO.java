/**
 * 
 */
package com.smthit.task.spi.model;

import java.util.Date;

import lombok.Data;

/**
 * @author Bean
 *
 */
@Data
public class TaskVO {
	private Long id;
	/**任务名*/
	private String name;
	/**任务编号*/
	private String taskNo;
	/**任务状态*/
	private Integer taskState;
	/**总步长*/
	private Integer totalStep;
	/**当前步长*/
	private Integer currentStep;
	/**任务开始时间*/
	private Date beginTime;
	/**任务结束时间*/
	private Date endTime;
	/**执行是否成功*/
	private Boolean success;
	/**执行的接口*/
	private String result;
	/**扩展信息*/
	private String ext;
	/**执行时长*/
	private Long duration;
	/**任务归属人*/
	private Long ownerUserId;
	/**任务类型*/
	private Integer taskType;
}
