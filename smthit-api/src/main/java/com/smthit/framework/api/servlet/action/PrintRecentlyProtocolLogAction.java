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
public class PrintRecentlyProtocolLogAction extends AbstractAction {

	/* (non-Javadoc)
	 * @see com.bluerabiit.woke.common.api.servlet.AbstractAction#doAction(javax.servlet.http.HttpServletRequest, java.util.Map)
	 */
	@Override
	public String doAction(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		return "log";
	}
}
