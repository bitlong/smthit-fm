/**
 * 
 */
package com.smthit.framework.api.common;

/**
 * @author Bean
 *
 */
public enum ResponseStatus {
	NOT_LOGIN(501, "当前会话已经失效或者异地登录."), 
	NOT_EXIST_USER(404, "用户不存在");

	private int status;
	private String desc;

	private ResponseStatus(int status, String desc) {
		this.status = status;
		this.desc = desc;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
