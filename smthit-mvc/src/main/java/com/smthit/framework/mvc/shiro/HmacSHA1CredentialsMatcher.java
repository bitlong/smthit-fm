/**
 * 
 */
package com.smthit.framework.mvc.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.codec.CodecSupport;

import com.smthit.lang.utils.IntegerUtils;

/**
 * @author Bean
 * //pbkdf2:sha1:1000$jn97Mfdq$0fa29e3115c7a46da523588b53ec76bee2009631
 */
public class HmacSHA1CredentialsMatcher implements CredentialsMatcher {

	public HmacSHA1CredentialsMatcher() {
	}

	@Override
	public boolean doCredentialsMatch(AuthenticationToken paramAuthenticationToken, AuthenticationInfo paramAuthenticationInfo) {
		
		Object accountPwd = paramAuthenticationInfo.getCredentials();
		Object token = paramAuthenticationToken.getCredentials();
		
		return equals(token, accountPwd);
	}

	private boolean equals(Object token, Object accountPwd) {
		
		if(accountPwd != null && accountPwd instanceof String) {
			String[] arrayAccount = StringUtils.split((String)accountPwd, '$');
			
			
			if(arrayAccount.length != 3) {
				return false;
			}
			
			String oldToken = arrayAccount[2];
			String salt = arrayAccount[1];
			
			String[] params = StringUtils.split(arrayAccount[0], ':');
			if(params.length != 3) {
				return false;
			}
			
			int iterations = IntegerUtils.parseInteger(params[2], 1000); 
			
			HmacSHA1Hash hash = new HmacSHA1Hash(token, salt, iterations);

			if(hash.toHex().equals(oldToken)) {
				return true;
			} else {
				return false;
			}
		}
		
		return false;
	}

}
