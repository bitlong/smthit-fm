/**
 * 
 */
package com.smthit.lang.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @author Bean
 *
 */
public class SmthitHttpMessageObjectMapper extends ObjectMapper {
	private static final long serialVersionUID = 1813144267202182125L;

	public SmthitHttpMessageObjectMapper() {
		super();
		
		SimpleModule module = new SimpleModule();
		
		module.addSerializer(Long.TYPE, LongJsonSerializer.instance());
		module.addSerializer(Long.class, LongJsonSerializer.instance());
		
		this.registerModule(module);
	}
}
