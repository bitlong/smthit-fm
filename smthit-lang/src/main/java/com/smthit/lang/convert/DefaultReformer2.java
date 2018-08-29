/**
 * 
 */
package com.smthit.lang.convert;

import com.smthit.lang.exception.ServiceException;
import com.smthit.lang.utils.BeanUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Bean
 *
 */
@Slf4j
public class DefaultReformer2<PO, VO> extends AbstractConvert<PO, VO> {
	protected Class<VO> voCls;

	public DefaultReformer2(Class<VO> cls) {
		this.voCls = cls;
	}

	@Override
	public VO toVO(PO po) {
		try {
			VO vo = (VO)voCls.newInstance();
			BeanUtil.copyPropertiesFromBean2Bean(po, vo);
			return vo;
		} catch (Exception exp) {
			log.error("对象转换失败.", exp);
			throw new ServiceException("从PO转换VO失败, VO class path = " + voCls.getCanonicalName() + " :" + exp.getMessage());
		}
	}
}
