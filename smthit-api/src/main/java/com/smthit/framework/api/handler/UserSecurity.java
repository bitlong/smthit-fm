/**
 * 
 */
package com.smthit.framework.api.handler;

/**
 * @author Bean
 *
 */
public interface UserSecurity {
	/**
	 * 判断用户是否登陆
	 * @param sid
	 * @return
	 * 
	 * @since 1.0
	 */
	public boolean isLogin(String sid);

	/**
	 * 
	 * @param sid
	 */
	public void initUserContext(String sid);
}
