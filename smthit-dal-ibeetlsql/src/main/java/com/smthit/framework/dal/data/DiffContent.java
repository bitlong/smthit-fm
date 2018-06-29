/**
 * 
 */
package com.smthit.framework.dal.data;

/**
 * @author Bean
 *
 */
public class DiffContent {
	private String key;
	private String src;
	private String dest;

	public DiffContent(String key, String src, String dest) {
		this.key = key;
		this.src = src;
		this.dest = dest;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the src
	 */
	public String getSrc() {
		return src;
	}

	/**
	 * @param src
	 *            the src to set
	 */
	public void setSrc(String src) {
		this.src = src;
	}

	/**
	 * @return the dest
	 */
	public String getDest() {
		return dest;
	}

	/**
	 * @param dest
	 *            the dest to set
	 */
	public void setDest(String dest) {
		this.dest = dest;
	}

}
