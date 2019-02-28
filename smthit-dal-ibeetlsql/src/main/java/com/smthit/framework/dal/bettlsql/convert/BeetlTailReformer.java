/**
 * 
 */
package com.smthit.framework.dal.bettlsql.convert;

import java.util.Map;

import org.beetl.sql.core.TailBean;

import com.smthit.lang.convert.DefaultReformer2;
import com.smthit.lang.utils.BeanUtil;


/**
 * @author Bean
 *
 */
public class BeetlTailReformer<PO, VO> extends DefaultReformer2<PO, VO> {

	public BeetlTailReformer(Class<VO> cls) {
		super(cls);
	}

	@Override
	public VO toVO(PO po) {
		VO vo = super.toVO(po);

		if (TailBean.class.isAssignableFrom(po.getClass())) {
			TailBean tail = (TailBean) po;

			Map<String, Object> tails = tail.getTails();
			
			BeanUtil.copyPropertiesFromMap2Bean(vo, tails);
		}

		return vo;
	}

}
