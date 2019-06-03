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
public enum EnumGenderStatus implements EnumStatus {
	MALE(1, "男"),
	FEMALE(2, "女"),
	UNKOWN(3, "待定");
	
	private int value;
	private String desc;
}
