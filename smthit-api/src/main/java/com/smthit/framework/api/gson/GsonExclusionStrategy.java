/**
 * 
 */
package com.smthit.framework.api.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.smthit.framework.api.protocol.RequestHeader;
import com.smthit.framework.api.protocol.ResponseHeader;

/**
 * @author Bean
 *
 */
public class GsonExclusionStrategy implements ExclusionStrategy {

	public boolean shouldSkipClass(Class<?> clazz) {
		if(clazz.equals(RequestHeader.class) || clazz.equals(ResponseHeader.class)) {
			return true;
		}
		return false;
	}
	
	public boolean shouldSkipField(FieldAttributes f) {
		return false;
	}
}
