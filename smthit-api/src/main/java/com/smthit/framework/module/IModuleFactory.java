/**
 * 
 */
package com.smthit.framework.module;

import org.springframework.context.ApplicationContext;

/**
 * @author Bean
 *
 */
public interface IModuleFactory {
	public <T> T getService(Class<T> clz);
	public ApplicationContext getApplicationContext();
}
