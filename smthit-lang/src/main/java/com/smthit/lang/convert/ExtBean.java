/**
 * 
 */
package com.smthit.lang.convert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bean
 *
 */
public class ExtBean {
	protected Map<String,Object> extMap = new HashMap<String,Object>();
	
	public Object get(String key){
		return extMap.get(key);
	}
	
	public void set(String key,Object value){
		extMap.put(key, value);
		
	}

	public Map<String, Object> getExt() {
		return extMap;
	}

	public void addAll(Map<String, Object> newExt) {
		extMap.putAll(newExt);
	}
	
	public boolean hasExt() {
		return extMap.size() != 0;
	}
}
