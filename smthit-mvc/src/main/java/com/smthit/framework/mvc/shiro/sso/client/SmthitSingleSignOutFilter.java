/**
 * 
 */
package com.smthit.framework.mvc.shiro.sso.client;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.smthit.framework.mvc.shiro.SmthitSessionManager;

/**
 * @author Bean
 * 单点登出
 */
public class SmthitSingleSignOutFilter extends AdviceFilter {
	private static Logger logger = LoggerFactory.getLogger(SmthitSingleSignOutFilter.class);
	
	@Autowired
	private SmthitSessionManager smthitSessionManager;
	
	public SmthitSingleSignOutFilter() {
	}

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		
		if(httpRequest.getRequestURI().contains("/logoutSSO")) {
			String token = httpRequest.getParameter("__token__");
			logger.info("Try to stop session with token = " + token);
			smthitSessionManager.stop(token);
		}
		
		return false;
	}

	@Override
	public void destroy() {
	}

}
