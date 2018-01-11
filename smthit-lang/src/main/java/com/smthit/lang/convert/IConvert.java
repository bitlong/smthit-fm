/**
 * 
 */
package com.smthit.lang.convert;

import java.util.List;


/**
 * @author Bean
 *
 */
public interface IConvert<PO, VO> {
	public VO toVO(PO po);
	public List<VO> toVOs(List<PO> pos);
}
