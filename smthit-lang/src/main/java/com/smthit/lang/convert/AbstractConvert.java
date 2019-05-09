/**
 * 
 */
package com.smthit.lang.convert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Bean
 *
 */
public abstract class AbstractConvert<PO, VO> implements IConvert<PO, VO> {

	public AbstractConvert() {
	}

	/**
	 * 将一组PO对象转换成VO对象
	 */
	@Override
	public List<VO> toVOs(List<PO> pos) {
		if(pos == null || pos.size() == 0) {
			return Collections.emptyList();
		}
		
		List<VO> result = new ArrayList<VO>();
		for(PO p : pos) {
			result.add(toVO(p));
		}
		
		return result;

	}	
	
	/**
	 * 将一组PO对象转换成VO对象，同时调用后处理器postHandler进行扩展变化
	 */
	@Override
	public List<VO> toVOs(List<PO> pos, IConvertPostHandler<PO, VO> postHandler) {
		if(pos == null || pos.size() == 0) {
			return Collections.emptyList();
		}
		
		List<VO> result = new ArrayList<VO>();
		for(PO p : pos) {
			VO v = toVO(p);
			if(postHandler != null)
				postHandler.post(p, v);
			result.add(v);
		}
		
		return result;
	}
	
	/**
	 * 将单个PO对象转换成VO对象，同时调用后处理器postHandler进行扩展变化
	 */
	@Override
	public VO toVO(PO po, IConvertPostHandler<PO, VO> postHandler) {
		if(po == null) {
			return null;
		}
		
		VO vo = toVO(po);
		
		if(postHandler != null)
			postHandler.post(po, vo);
		
		return vo;
	}
}
