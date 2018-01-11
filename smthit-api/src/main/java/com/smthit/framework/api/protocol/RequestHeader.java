/**
 * 
 */
package com.smthit.framework.api.protocol;

import com.google.common.base.MoreObjects;


/**
 * @author Bean
 * @since 1.0.0
 */
public class RequestHeader {
	public static final String VERSION = "version";
	public static final String AGENT = "agent";
	public static final String SID = "jsessionid";
	public static final String TIME = "time";
	public static final String PROTOCOL = "protocol-id";
	public static final String SHADOW = "shadow";
	
	
	/**
	 * 请求协议的版本号
	 * @since 1.0.0
	 */
	private double version;
	/**
	 * 客户端机型
	 * @since 1.0.0
	 */
	private String agent;
	/**
	 * 会话ID，用于标识客户端用户身份信息
	 * @since 1.0.0
	 */
	private String sid;
	/**
	 * 请求发起的时间戳
	 * @since 1.0.0
	 */
	private long time;
	/**
	 * 协议的ID编号
	 * @since 1.0.0
	 */
	private String protocol;
	/**
	 * 协议的指纹码
	 * @since 1.0.0
	 */
	private String shadow;
	
	/**
	 * 协议的格式
	 * @since 1.0.0
	 */
	private String contentType;
	
	/**
	 * 内容编码格式
	 */
	private String charsetEncoding;
	
	public double getVersion() {
		return version;
	}

	public void setVersion(double version) {
		this.version = version;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getShadow() {
		return shadow;
	}

	public void setShadow(String shadow) {
		this.shadow = shadow;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public void setCharsetEncoding(String characterEncoding) {
		this.charsetEncoding = characterEncoding;
	}

	public String getCharsetEncoding() {
		return charsetEncoding;
	}
	
	public String getWokeHeader() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("client-version").append(":").append(version).append(";");
		sb.append("protocol-id").append(":").append(protocol).append(";");
		sb.append("shadow").append(":").append(shadow);
		
		return sb.toString();
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("version", version)
				.add("sid", sid)
				.add("agent", agent)
				.add("time", time)
				.add("protocolId", protocol)
				.add("shadow", shadow).toString();
	}
}
