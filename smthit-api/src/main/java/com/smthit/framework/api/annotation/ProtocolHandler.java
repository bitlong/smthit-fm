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
@Target({ElementType.TYPE})
@Retention(RUNTIME)
public @interface ProtocolHandler {
	String protocol();
	Class<?> request();
	Class<?> response();
	String desc() default "";
}
