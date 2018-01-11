/**
 * 
 */
package com.smthit.lang.exception;

/**
 * @author Bean
 *
 */
public class ObjectNotFoundException extends ServiceException {

	public ObjectNotFoundException() {
		super("对象不存在.");
	}

	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4502215380498818784L;

}
