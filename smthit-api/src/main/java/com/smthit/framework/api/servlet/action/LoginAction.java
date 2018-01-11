/**
 * 
 */
package com.smthit.framework.api.servlet.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import com.smthit.framework.api.servlet.AbstractAction;

/**
 * @author Bean
 *
 */
public class LoginAction extends AbstractAction {
	private static Logger logger = Logger.getLogger(LoginAction.class);
	
	@Override
	public String doAction(HttpServletRequest request, HttpServletResponse response,  Map<String, Object> model) {
		try {
			String mobile = ServletRequestUtils.getStringParameter(request, "mobile");
			String password = ServletRequestUtils.getStringParameter(request, "password");
			
			if(StringUtils.isNotBlank(mobile) && StringUtils.isNotBlank(password)) {
				request.getSession(true).setAttribute("login", true);
				
				return "redirect:" + request.getServletContext().getContextPath() + "/debug-api?action=index";
			}
		} catch (ServletRequestBindingException e) {
			logger.error(e.getMessage(), e);
		}
		
		return "login";
	}
}
