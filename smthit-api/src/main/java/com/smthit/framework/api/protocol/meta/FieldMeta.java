/**
 * 
 */
package com.smthit.framework.api.protocol.meta;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bean
 * @since
 */
public class FieldMeta {
	private String name;
	private double since;
	private double until;
	private String type;
	
	private List<FieldMeta> subFields = new ArrayList<FieldMeta>();

	public FieldMeta(String name, double since, double until, String type) {
		this.name = name;
		this.since = since;
		this.until = until;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<FieldMeta> getSubFields() {
		return subFields;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("    ");
		sb.append(name)
			.append(", ").append(type)
			.append(", since ").append(since).append(" until ")
			.append(until == Double.MAX_VALUE ? "unlimited" : until).append("\n");
		
		if(subFields.size() > 0) {
			sb.append("    {\n");
			for(FieldMeta meta : subFields) {
				sb.append("    ");
				sb.append(meta.toString());
			}
			sb.append("    }\n");
		}
		
		
				
		return sb.toString();
	}
}
