/**
 * 
 */
package com.smthit.framework.dal.bettlsql;

import java.util.List;

import org.beetl.sql.core.engine.PageQuery;

/**
 * @author Bean
 * TODO 目前这个类没有神什么用，将来需要加上对数据库中视图的映射，这样可以简化应用开发逻辑。但是View中的数据不是实体数据，不存在表的映射
 * 
 */
public interface BaseViewMapper<T> {

	/**
	 * 返回实体对应的所有数据库记录
	 * @return
	 */
	List<T> all();
	/**
	 * 返回实体对应的一个范围的记录
	 * @param start
	 * @param size
	 * @return
	 */
	List<T> all(int start,int size);
	/**
	 * 返回实体在数据库里的总数
	 * @return
	 */
	long allCount();
	
	/**
	 * 模板查询，返回符合模板得所有结果。beetlsql将取出非null值（日期类型排除在外），从数据库找出完全匹配的结果集
	 * @param entity
	 * @return
	 */
	List<T> template(T entity);
	
	/**
	 * 模板查询，返回一条结果,如果没有，返回null
	 * @param entity
	 * @return
	 */
	T templateOne(T entity);

	List<T> template(T entity,int start,int size);
	
	void templatePage(PageQuery<T> query);
	/**
	 * 符合模板得个数
	 * @param entity
	 * @return
	 */
	long templateCount(T entity);	
}
