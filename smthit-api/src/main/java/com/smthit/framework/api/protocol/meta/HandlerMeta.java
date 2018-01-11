package com.smthit.framework.api.protocol.meta;

/**
 * 
 * @author Bean
 * @since
 */
public class HandlerMeta {

	private String protocol;
	private double since;
	private double until;
	private String methodName;

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public double getSince() {
		return since;
	}

	public void setSince(double since) {
		this.since = since;
	}

	public double getUntil() {
		return until;
	}

	public void setUntil(double until) {
		this.until = until;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("methodName = " + methodName);
		sb.append(", since ").append(since);
		sb.append(" until ").append(until == Double.MAX_VALUE ? "unlimited" : until).append("\n");
		
		return sb.toString();
	}
}
