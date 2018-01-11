/**
 * 
 */
package com.smthit.framework.api.annotation;



import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;

/**
 * @author Bean
 *
 */
public class HandlerBeanNameGenerator implements BeanNameGenerator {
	private static Logger logger = Logger.getLogger(HandlerBeanNameGenerator.class);
		
	public String generateBeanName(BeanDefinition definition,
			BeanDefinitionRegistry registry) {
		logger.debug(definition.getBeanClassName());
		return definition.getBeanClassName();
	}
}
