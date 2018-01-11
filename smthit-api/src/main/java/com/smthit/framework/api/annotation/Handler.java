/**
 * 
 */
package com.smthit.framework.api.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Bean
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Handler {
	double since();
	double until() default Double.MAX_VALUE;
	boolean require_login() default true;
	String desc() default "";
}
