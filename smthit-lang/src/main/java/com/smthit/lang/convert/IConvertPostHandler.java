/**
 * 
 */
package com.smthit.lang.convert;

/**
 * @author Bean
 *
 */
public interface IConvertPostHandler<PO, VO> {
	public void post(PO po, VO vo);
}
