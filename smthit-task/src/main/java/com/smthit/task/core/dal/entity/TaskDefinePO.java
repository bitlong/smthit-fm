/**
 * 
 */
package com.smthit.task.core.dal.entity;

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
@Table(name = "sm_task_defines")
public class TaskDefinePO extends BaseEntity {
	@AutoID
	private Long id;
	private String name;
	private String key;
}
