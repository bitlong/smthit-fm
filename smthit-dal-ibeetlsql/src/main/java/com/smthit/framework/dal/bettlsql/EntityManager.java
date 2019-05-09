/**
 * 
 */
package com.smthit.framework.dal.bettlsql;

import static com.smthit.framework.dal.bettlsql.SqlKit.$;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import com.smthit.framework.dal.data.PageParam;
import com.smthit.framework.dal.data.Pagination;
import com.smthit.framework.dal.data.PaginationUtils;
import com.smthit.lang.convert.AbstractConvert;
import com.smthit.lang.exception.ServiceException;
import com.smthit.lang.utils.BeanUtil;

/**
 * @author Bean
 *
 */
public interface EntityManager<T extends ActiveRecord> {

	@Transactional
	default public T update(Serializable id, Map<String, Object> params, String[] ignoreProperties) {
		return update(id, params, ignoreProperties, true);
	}

	default public T update(Serializable id, Map<String, Object> params, String[] ignoreProperties, boolean ignoreEmptyProperty) {
		try {
			T entity = $().single(entityCls(), id);

			if (null != entity) {
				BeanUtil.copyPropertiesFromMap2Bean(entity, params, ignoreProperties);
				entity.update(ignoreEmptyProperty);
			}

			return entity;
		} catch (Exception e) {
			throw new ServiceException("Update Entity Failed!");
		}

	}
	
	@Transactional
	default public T update(Serializable id, Map<String, Object> params) {
		return update(id, params, new String[0], true);
	}
	
	@Transactional
	default public T update(Serializable id, Map<String, Object> params, boolean ignoreEmptyProperty) {
		return update(id, params, new String[0], ignoreEmptyProperty);
	}

	default public T create(Map<String, Object> params, String[] ignoreProperties) {
		try {
			T entity = entityCls().newInstance();
			BeanUtil.copyPropertiesFromMap2Bean(entity, params, ignoreProperties);
			entity.saveDo();
			return entity;
		} catch (Exception e) {
			throw new ServiceException("Create Entity Failed!");
		}
	}
	
	@Transactional
	default public T create(Map<String, Object> params) {
		return create(params, new String[0]);
	}

	@Transactional
	default public T update(Serializable id, Object params, String[] ignoreProperties) {
		return update(id, params, ignoreProperties, true);
	}
	
	@Transactional
	default public T update(Serializable id, Object params, String[] ignoreProperties, boolean ignoreEmptyProperty) {
		try {
			T entity = $().single(entityCls(), id);

			if (null != entity) {
				BeanUtils.copyProperties(params, entity, ignoreProperties);
				entity.update(ignoreEmptyProperty);
			}

			return entity;
		} catch (Exception e) {
			throw new ServiceException("Update Entity Failed!");
		}
	}

	@Transactional
	default public T update(Serializable id, Object params) {
		return update(id, params, new String[] {"id"}, true);
	}

	@Transactional
	default public T update(Serializable id, Object params, boolean ignoreEmptyProperty) {
		return update(id, params, new String[] {"id"}, ignoreEmptyProperty);
	}

	
	@Transactional
	default public T create(Object params, String[] ignoreProperties) {
		try {
			T entity = entityCls().newInstance();
			BeanUtils.copyProperties(params, entity, ignoreProperties);
			entity.saveDo();
			return entity;
		} catch (Exception e) {
			throw new ServiceException("Create Entity Failed!");
		}
	}
	
	@Transactional
	default public T create(Object params) {
		return create(params, new String[0]);
	}

	@Transactional
	default public <VO> Pagination<VO> pageVO(PageParam pageParam, AbstractConvert<T, VO> reformer) {
		PageQuery<T> pageQuery = new PageQuery<T>(pageParam.getPageNumber(), pageParam.getPageSize(), pageParam.getParams());
		
		pageQuery.setTotalRow($().templateCount(entityCls(), pageParam.getParams()));
				
		long start = ($().isOffsetStartZero() ? 0 : 1) + (pageQuery.getPageNumber() - 1) * pageQuery.getPageSize();
		long size = pageQuery.getPageSize();
		List<T> list = $().template(entityCls(), pageQuery.getParas(), start, size, pageParam.getOrderBy());
		
		pageQuery.setList(list);

		return PaginationUtils.convertPagination(pageQuery, reformer);
	}

	@Transactional
	default public <VO> List<VO> listVO(PageParam qc, AbstractConvert<T, VO> reformer) {
		long start = ($().isOffsetStartZero() ? 0 : 1) + (qc.getPageNumber() - 1) * qc.getPageSize();
		long size = qc.getPageSize();
		
		List<T> result = $().template(entityCls(), qc.getParams(), start, size, qc.getOrderBy());
		 
		return reformer.toVOs(result);
	}
	
	@Transactional
	default public <VO> Pagination<VO> pageVO(String sqlId, PageParam pageParam, AbstractConvert<T, VO> reformer) {
		PageQuery<T> pageQuery = new PageQuery<T>(pageParam.getPageNumber(), 
				pageParam.getPageSize(), 
				pageParam.getParams());
		pageQuery.setOrderBy(pageParam.getOrderBy());
		
		$().pageQuery(sqlId, entityCls(), pageQuery);
		
		return PaginationUtils.convertPagination(pageQuery, reformer);
	}
	
	/**
	 * 返回所有复核条件的对象
	 * @param qc
	 * @param reformer
	 * @return
	 */
	default public <VO> List<VO> allVO(T qc, AbstractConvert<T, VO> reformer) {
		List<T> result = $().template(qc);
		return reformer.toVOs(result);
	}
	
	default public <VO> VO getVO(Object pk, AbstractConvert<T, VO> reformer) {
		T t = $().unique(entityCls(), pk);
		return reformer.toVO(t);
	}
	
	@SuppressWarnings({"unchecked" })
	default public Class<T> entityCls() {
		Type[] types = this.getClass().getGenericInterfaces();
		
		for(Type type : types) {
			if(type instanceof ParameterizedType) {
				Class<T> cls = (Class<T>)((ParameterizedType)type).getActualTypeArguments()[0];
				if(ActiveRecord.class.isAssignableFrom(cls)) {
					return cls;	
				}
			}
		}
		
		throw new ServiceException("Can not find Entity Class by GenericType！");
	}
	
	default public SQLManager getSQLManager() {
		return $();
	}
}
