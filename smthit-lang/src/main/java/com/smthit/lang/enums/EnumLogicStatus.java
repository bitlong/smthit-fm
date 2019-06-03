/**
 * 
 */
package com.smthit.lang.enums;

import com.smthit.lang.utils.EnumStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Bean
 * @since 1.0.5
 */
@Getter
@AllArgsConstructor
public enum EnumLogicStatus implements EnumStatus {
	NORMAL(1, "正常"),
	DELETED(2, "删除");
	
	private int value;
	private String desc;
}
