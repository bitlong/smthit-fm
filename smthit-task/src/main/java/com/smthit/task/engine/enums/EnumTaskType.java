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
public enum EnumTaskType implements EnumStatus {
	USER(1, "用户任务"),
	SYS(2, "系统任务");

	private int value;
	private String desc;	
}
