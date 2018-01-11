/**
 * 
 */
package com.smthit.framework.api.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Bean
 *
 */
@Target({ElementType.PARAMETER})
@Retention(RUNTIME)
public @interface RequestParam {
	ParamType value() default ParamType.MAP;
}
