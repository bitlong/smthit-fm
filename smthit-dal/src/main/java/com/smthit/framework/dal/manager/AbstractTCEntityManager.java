/**
 * 
 */
package com.smthit.framework.dal.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.TCJpaRepository;

import com.smthit.framework.dal.criteria.Criteria;
import com.smthit.framework.dal.criteria.EntityModel;
import com.smthit.framework.dal.criteria.Restriction;
import com.smthit.framework.dal.data.Pagination;
import com.smthit.lang.exception.ObjectNotFoundException;
import com.smthit.lang.utils.BeanUtil;

/**
 * @author Bean
 *
 */
public abstract class AbstractTCEntityManager<T> implements IEntityManager<T>,
		IEntityQuery<T> {
	
	@Override
	public T getById(Long id) {
		return getRepository().findOne(id);
	}

	public List<T> findByIds(List<Long> ids) {
		List<T> result = new ArrayList<T>();
		
		Iterable<T> itr = getRepository().findAll(ids);
		while(itr.iterator().hasNext()) {
			result.add(itr.iterator().next());
		}
		
		return result;
	}
	
	public Map<Long, T> findByIds2Map(List<Long> ids) {
		Map<Long, T> result = new HashMap<Long, T>();
		
		Iterable<T> itr = getRepository().findAll(ids);
		while(itr.iterator().hasNext()) {
			T em = itr.iterator().next();
			result.put(((EntityModel)em).getId(), (T)em);
		}
		
		return result;
	}
	
	public Pagination<T> pagingData(Pageable pageable) {
		return new Pagination<T>(getRepository().findAll(new Criteria<T>(), pageable));
	}

	public Pagination<T> pagingData(Restriction<T> restriction) {
		return new Pagination<T>(getRepository().findAll(restriction.getCriteria(), restriction.getPageable()));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T update(Long id, Map<String, Object> params) {
		T obj = getRepository().findOne(id);
		
		if(obj == null) {
			throw new ObjectNotFoundException();
		}
		
		params.put("updateAt", new Date());
		
		return getRepository().save((T)BeanUtil.copyPropertiesFromMap2Bean(obj, params, new String[]{"id"}));
	}
	
	@Override
	public void delete(Long id) {
		getRepository().delete(id);
	}

	@Override
	public void delete(T t) {
		getRepository().delete(t);
	}
	
	@Override
	@Deprecated
	public T create(Map<String, Object> params) {
		throw new UnsupportedOperationException("deprecated.");
	}

	@Override
	public void save(T t) {
		if(t instanceof EntityModel){
			EntityModel em = (EntityModel)t;
			if(em.getCreatedAt() == null) {
				em.setCreatedAt(new Date());
			}
			em.setUpdatedAt(new Date());
		}
		getRepository().save(t);
	}
	
	public abstract TCJpaRepository<T, Long> getRepository();
}
