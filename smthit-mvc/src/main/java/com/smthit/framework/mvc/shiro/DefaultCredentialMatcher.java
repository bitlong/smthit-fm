/**
 * 
 */
package com.smthit.framework.mvc.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * @author Bean
 *
 */
public class DefaultCredentialMatcher implements CredentialsMatcher {

	/**
	 * 
	 */
	public DefaultCredentialMatcher() {
	}

	@Override
	public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {

		Object accountPwd = authenticationInfo.getCredentials();
		Object token = authenticationToken.getCredentials();

		if(accountPwd != null && token != null) {
			if(token instanceof String) {
				return accountPwd.equals(token);	
			} else  if(token instanceof byte[]) {
				String tokenString = new String((byte[])token);
				return accountPwd.equals(tokenString);
			} else if(token instanceof char[]) {
				String tokenString = new String((char[])token);
				return accountPwd.equals(tokenString);
			}
			
		}
		
		return false;
	}

}
