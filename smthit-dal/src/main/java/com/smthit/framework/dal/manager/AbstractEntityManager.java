/**
 * 
 */
package com.smthit.framework.dal.manager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.smthit.framework.dal.criteria.Criteria;
import com.smthit.framework.dal.criteria.EntityModel;
import com.smthit.framework.dal.criteria.IRepository;
import com.smthit.framework.dal.criteria.Restriction;
import com.smthit.framework.dal.data.Pagination;
import com.smthit.lang.exception.ObjectNotFoundException;
import com.smthit.lang.utils.BeanUtil;

/**
 * @author Bean
 *
 */
public abstract class AbstractEntityManager<T> implements IEntityManager<T>,
		IEntityQuery<T>, ApplicationContextAware {
	private static Logger logger = LoggerFactory.getLogger(AbstractEntityManager.class);
	
	@Autowired
	private ApplicationContext applicationContext;
	
	private IRepository<T> repository;
	
	@Override
	public T getById(Long id) {
		return getRepository().findOne(id);
	}

	public Pagination<T> pagingData(Pageable pageable) {
		return new Pagination<T>(getRepository().findAll(new Criteria<T>(), pageable));
	}

	public Pagination<T> pagingData(Restriction<T> restriction) {
		return new Pagination<T>(getRepository().findAll(restriction.getCriteria(), restriction.getPageable()));
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public T update(Long id, Map<String, Object> params) {
		T obj = getRepository().findOne(id);
		
		if(obj == null) {
			throw new ObjectNotFoundException();
		}
		
		params.put("updateAt", new Date());
		
		return getRepository().save((T)BeanUtil.copyPropertiesFromMap2Bean(obj, params, new String[]{"id"}));
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		getRepository().delete(id);
	}

	@Override
	@Transactional
	public void delete(T t) {
		getRepository().delete(t);
	}

	@Override
	@Transactional
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

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public T create(Map<String, Object> params) {
		if(params.size() == 0) {
			//TODO
			return null;
		}
		
		params.put("createAt", new Date());
		params.put("updateAt", new Date());

		return getRepository().save((T)BeanUtil.copyPropertiesFromMap2Bean(createEmptyData(), params, new String[] {"id"}));
	}
	
	@SuppressWarnings("unchecked")
	public IRepository<T> getRepository() {
		if(repository == null) {
			Class<?> modelCls = entityCls();
			String repositoryName = repositoryName(modelCls.getSimpleName());
			repository = (IRepository<T>) applicationContext.getBean(repositoryName); 
			if(repository == null) {
				logger.error("Can't find repository :" + repositoryName + " to Model : " + modelCls.getName());
				throw new NoSuchBeanDefinitionException(repositoryName);
			}
		}
		
		return repository;
	}
	
	@SuppressWarnings("unchecked")
	public T createEmptyData() {
		Type type2 = this.getClass().getGenericSuperclass();
		Class<?> cls = (Class<?>)((ParameterizedType)type2).getActualTypeArguments()[0]; 	
		try {
			return (T)cls.newInstance();
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings({"unchecked" })
	private Class<T> entityCls() {
		Class<T> cls = (Class<T>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]; 
		return cls;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	/**
	 * 获取默认的Bean的名称
	 * @param modelName
	 * @return
	 */
	private String repositoryName(String modelName) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(StringUtils.lowerCase(modelName.charAt(0) + ""));
		if(modelName.endsWith("PO")) {
			sb.append(modelName.subSequence(1, modelName.length() - 2));
		} else {
			sb.append(modelName.subSequence(1,  modelName.length()));
		}
		
		sb.append("Repository");
		
		return sb.toString();
	}
}
