/**
 * 
 */
package com.smthit.task.engine.enums;

import com.smthit.lang.utils.EnumStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Bean
 *
 */
@Getter
@AllArgsConstructor
public enum EnumTaskState implements EnumStatus {
	CREATED(1, "创建"),
	EXECUTING(2, "执行中"),
	SUSPEND(3, "挂起"),
	OVER(200, "结束");
	
	private int value;
	private String desc;
}
