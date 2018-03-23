/**
 * 
 */
package com.smthit.lang.fluent.validator;

import com.baidu.unbiz.fluentvalidator.ValidatorContext;
import com.baidu.unbiz.fluentvalidator.ValidatorHandler;

import lombok.Data;

/**
 * @author Bean
 *
 */
@Data
public class LengthValidator extends ValidatorHandler<String> {
	private int min = 0;
	private int max = Integer.MAX_VALUE;
	
	public LengthValidator(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	@Override
	public boolean validate(ValidatorContext context, String t) {
		return super.validate(context, t);
	}

}
