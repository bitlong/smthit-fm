/**
 * 
 */
package com.smthit.framework.api.servlet;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Bean
 *
 */
public abstract class AbstractAction {
		public abstract String doAction(HttpServletRequest request, HttpServletResponse resp, Map<String, Object> model);		
}
