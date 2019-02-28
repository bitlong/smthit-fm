/**
 * 
 */
package com.smthit.framework.mvc.thymeleaf.dialect;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

/**
 * @author Bean
 *
 */
public class SmthitDialect2 extends AbstractProcessorDialect {
    private static final String NAME = "smthit";  
    private static final String PREFIX = "sm2";  

    public SmthitDialect2() {
        super(NAME, PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);  
    }
    

	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        
        processors.add(new MenuTagProcessor2(PREFIX));
        processors.add(new OptionTagProcessor(PREFIX));
        
		return processors;
	}
 
}
