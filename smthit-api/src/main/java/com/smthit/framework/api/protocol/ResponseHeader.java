/**
 * 
 */
package com.smthit.framework.api.protocol;

import com.google.common.base.MoreObjects;

/**
 * @author Bean
 * @since 1.0.0
 */
public class ResponseHeader {
	/**
	 * 协议版本
	 * 
	 * @since 1.0.0
	 */
	private String version;
	/**
	 * 当前请求的会话ID
	 * 
	 * @since 1.0.0
	 */
	private String sid;
	/**
	 * 请求处理的时长
	 * 
	 * @since 1.0.0
	 */
	private long duration;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("version", version)
				.add("sid", sid)
				.add("duration", duration)
				.toString();
	}

}
