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
public class LogoutAction extends AbstractAction {

	@Override
	public String doAction(HttpServletRequest request,
			HttpServletResponse resp, Map<String, Object> model) {
		request.getSession(true).removeAttribute("login");
		return "redirect:/debug-api?action=logout";
	}

}
