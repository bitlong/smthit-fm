/**
 * 
 */
package com.smthit.task.core.dal.entity;

import java.util.Date;

import org.beetl.sql.core.annotatoin.AutoID;
import org.beetl.sql.core.annotatoin.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Bean
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "sm_task_logs")
public class TaskLogPO extends BaseEntity {
	@AutoID
	private Long id;
	private Long taskId;
	private Integer currentStep;
	private Date executeTime;
	private Integer taskState;
}
