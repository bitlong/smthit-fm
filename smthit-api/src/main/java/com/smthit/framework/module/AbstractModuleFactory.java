/**
 * 
 */
package com.smthit.framework.module;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Bean
 *
 */
public abstract class AbstractModuleFactory implements IModuleFactory {

	protected ClassPathXmlApplicationContext applicationContext;

	private String[] contextLocation;
	
	public void init() {
		applicationContext = new ClassPathXmlApplicationContext(contextLocation);
	}
	
	public void destroy() {
		applicationContext.close();
	}

	public <T> T getService(Class<T> clz) {
		return (T)getApplicationContext().getBean(clz);
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	

	/**
	 * @param contextLocation the contextLocation to set
	 */
	public void setContextLocation(String ... contextLocation) {
		this.contextLocation = contextLocation;
	}
}
