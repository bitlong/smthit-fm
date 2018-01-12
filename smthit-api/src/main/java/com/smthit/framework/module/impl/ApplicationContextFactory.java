/**
 * 
 */
package com.smthit.framework.module.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import com.smthit.framework.module.AbstractModuleFactory;

/**
 * @author Bean
 *
 */
public class ApplicationContextFactory {
	private static WebApplicationContext rootApplicationContext;
	/**
	 * 
	 */
	private ApplicationContextFactory() {
	}

	public static ApplicationContext getRootApplicationContext() {
		return rootApplicationContext;
	}
	
	public static void setRootApplicationContext(WebApplicationContext context) {
		rootApplicationContext = context;
	}
	
	public AbstractModuleFactory lookupModuleFactory(Class<?> factoryClass) {
		AbstractModuleFactory context = (AbstractModuleFactory)rootApplicationContext.getBean(factoryClass);
		return context;
	}
	
	public static <S, F> S lookupService(Class<F> factoryClass, Class<S> spiClass) {
		AbstractModuleFactory context = (AbstractModuleFactory)rootApplicationContext.getBean(factoryClass);
		return 	(S)context.getService(spiClass);
	}
}
