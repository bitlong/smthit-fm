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
	/**
	 * 对象转换器必须实现的一个方法，基本功能是实现Bean属性的拷贝
	 * @param po
	 * @return
	 */
	public VO toVO(PO po);
	
	public List<VO> toVOs(List<PO> pos);
	
	public VO toVO(PO po, IConvertPostHandler<PO, VO> postHandler);
	
	public List<VO> toVOs(List<PO> pos, IConvertPostHandler<PO, VO> postHandler);
		
}
