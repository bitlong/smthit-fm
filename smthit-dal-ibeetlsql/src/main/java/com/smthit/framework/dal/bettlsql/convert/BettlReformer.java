/**
 * 
 */
package com.smthit.framework.dal.bettlsql.convert;

import org.beetl.sql.core.TailBean;

import com.smthit.lang.convert.DefaultReformer2;
import com.smthit.lang.convert.ExtBean;

/**
 * @author Bean
 *
 */
public class BettlReformer<PO, VO> extends DefaultReformer2<PO, VO> {

	public BettlReformer(Class<VO> cls) {
		super(cls);
	}

	@Override
	public VO toVO(PO po) {
		VO vo = super.toVO(po);
		
		if(TailBean.class.isAssignableFrom(po.getClass()) &&
				ExtBean.class.isAssignableFrom(voCls)) {
			TailBean tail = (TailBean)po;
			ExtBean extBean = (ExtBean)vo;
			
			if(tail.getTails().size() > 0) {
				extBean.addAll(tail.getTails());
			}
		}
		
		return vo;
	}	
}
