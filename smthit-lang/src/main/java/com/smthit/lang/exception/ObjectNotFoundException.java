/**
 * 
 */
package com.smthit.lang.exception;

/**
 * @author Bean
 *
 */
public class ObjectNotFoundException extends ServiceException {
	private static final long serialVersionUID = -4502215380498818784L;
	
	public ObjectNotFoundException() {
		super("对象不存在.");
	}

	public ObjectNotFoundException(String msg) {
		super(msg);
	}
}
