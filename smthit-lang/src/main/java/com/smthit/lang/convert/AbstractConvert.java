/**
 * 
 */
package com.smthit.lang.convert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bean
 *
 */
public abstract class AbstractConvert<PO, VO> implements IConvert<PO, VO> {

	/**
	 * 
	 */
	public AbstractConvert() {
	}

	@Override
	public List<VO> toVOs(List<PO> pos) {
		List<VO> result = new ArrayList<VO>();
		for(PO p : pos) {
			result.add(toVO(p));
		}
		
		return result;

	}

}
