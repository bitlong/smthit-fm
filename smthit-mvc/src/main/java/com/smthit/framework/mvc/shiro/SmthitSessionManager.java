/**
 * 
 */
package com.smthit.framework.mvc.shiro;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Bean
 *
 */
public class SmthitSessionManager extends DefaultWebSessionManager {
	private static Logger logger = LoggerFactory.getLogger(SmthitSessionManager.class);
	
	private Map<String, String> serviceTokenToSessionCache = new HashMap<String, String>();
	
	/**
	 * 
	 */
	public SmthitSessionManager() {
	}

	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		String sid = request.getParameter("__sid");
		
		if(StringUtils.isNotBlank(sid)) {
			if(WebUtils.isTrue(request, "__cookie")) {
                HttpServletRequest rq = (HttpServletRequest)request;  
                HttpServletResponse rs = (HttpServletResponse)response;  
                Cookie template = getSessionIdCookie();  
                Cookie cookie = new SimpleCookie(template);  
                cookie.setValue(sid); cookie.saveTo(rq, rs);  
			}
			
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE,  
                    ShiroHttpServletRequest.URL_SESSION_ID_SOURCE); // session来源与url  
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sid);  
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            
            return sid; 
		} else {
			sid = (String)super.getSessionId(request, response);
		}
		
		//缓存Server传回来的Token
		String smthitServerToken = request.getParameter("__token__");
		if(serviceTokenToSessionCache.get(smthitServerToken) != null && !serviceTokenToSessionCache.get(smthitServerToken).equals(sid)) {
			serviceTokenToSessionCache.put(smthitServerToken, sid);
		}
		
		return sid; 
	}
	
	/**
	 * 根据toke 释放对应的session
	 * @param serviceToken
	 */
	public void stop(String serviceToken) {
		String sid = serviceTokenToSessionCache.get(serviceToken);
		if(StringUtils.isNoneEmpty(sid)) {
			SessionKey sessionKey = new DefaultSessionKey(sid);
			try {
				Session session = getSession(sessionKey);
				if(session != null) {
					new Subject.Builder().session(session).buildSubject().logout();
					stop(sessionKey);	
				}
			} catch (Exception exp) {
				logger.warn("The session is not exist when stop it with service's token! sid =" + sid);
			}
		}
	}
}
