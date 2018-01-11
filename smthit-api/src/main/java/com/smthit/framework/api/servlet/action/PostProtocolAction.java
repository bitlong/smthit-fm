/**
 * 
 */
package com.smthit.framework.api.servlet.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smthit.framework.api.servlet.AbstractAction;

/**
 * @author Bean
 *
 */
public class PostProtocolAction extends AbstractAction {

	@Override
	public String doAction(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		return "post";
	}
}
