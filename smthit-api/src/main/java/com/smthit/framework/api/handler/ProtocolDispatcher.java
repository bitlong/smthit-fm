/**
 * 
 */
package com.smthit.framework.api.handler;

import com.smthit.framework.api.common.ResponseStatus;
import com.smthit.framework.api.protocol.Request;
import com.smthit.framework.api.protocol.Response;

/**
 * @author Bean
 * @since 1.0.0
 */
public class ProtocolDispatcher {
	private ProtocolMapper mapper = ProtocolMapper.singleton();
	
	private UserSecurity userSecurity;
	
	/**
	 * “woke”:client-version:0.0.1;protocol-id:xxxx-0;shadow:MD5;c-time=long;
	 * @param request
	 * @return
	 * 
	 * @since 1.0.0
	 */
	public Response dispatch(Request request) throws Throwable {
		String protocol = request.getHeader().getProtocol();
		double version = request.getHeader().getVersion();
		
		ProtocolMethod pm = mapper.findProtocolMethod(protocol, version);
		
		if(pm.isRequireLogin() == true) {
			//check user whether login or not.
			if(!userSecurity.isLogin(request.getHeader().getSid())) {
				return new Response(ResponseStatus.NOT_LOGIN.getStatus(), ResponseStatus.NOT_LOGIN.getDesc());
			}
		}
		
		userSecurity.initUserContext(request.getHeader().getSid());
		
		Response response = pm.invoke(request);
		
		return response;
	}

	public void setUserSecurity(UserSecurity userSecurity) {
		this.userSecurity = userSecurity;
	}
}
