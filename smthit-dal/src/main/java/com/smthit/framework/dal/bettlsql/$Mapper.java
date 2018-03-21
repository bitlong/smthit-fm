/**
 * 
 */
package com.smthit.framework.dal.bettlsql;

import static com.smthit.framework.dal.bettlsql.SqlKit.$;

import java.util.List;

import org.beetl.sql.core.db.KeyHolder;
import org.beetl.sql.core.engine.PageQuery;

/**
 * @author Bean
 *
 */
@SuppressWarnings("unchecked")
public interface $Mapper<T> {

	/**
	 * 通用插入，插入一个实体对象到数据库，所以字段将参与操作，除非你使用ColumnIgnore注解
	 * @param entity
	 */
	default void insert(T entity) {
		$().insert(entity);
	}
	/**
	 * （数据库表有自增主键调用此方法）如果实体对应的有自增主键，插入一个实体到数据库，设置assignKey为true的时候，将会获取此主键
	 * @param entity
	 * @param autDbAssignKey 是否获取自增主键
	 */
	default void insert(T entity,boolean autDbAssignKey) {
		$().insert(entity, autDbAssignKey);
	}
	/**
	 * 插入实体到数据库，对于null值不做处理
	 * @param entity
	 */
	default void insertTemplate(T entity) {
		$().insertTemplate(entity);
	}
	/**
	 * 如果实体对应的有自增主键，插入实体到数据库，对于null值不做处理,设置assignKey为true的时候，将会获取此主键
	 * @param entity
	 * @param autDbAssignKey
	 */
	default void insertTemplate(T entity,boolean autDbAssignKey) {
		$().insertTemplate(entity, autDbAssignKey);
	}
	
	/**
	 * 批量插入实体。此方法不会获取自增主键的值，如果需要，建议不适用批量插入，适用
	 * <pre>
	 * insert(T entity,true);
	 * </pre>
	 * @param list
	 */
	default void insertBatch(List<T> list) {
		$().insertBatch(this.getClass(), list);
		
	}
	/**
	 * （数据库表有自增主键调用此方法）如果实体对应的有自增主键，插入实体到数据库，自增主键值放到keyHolder里处理
	 * @param entity
	 * @return
	 */
	default KeyHolder insertReturnKey(T entity) {
		KeyHolder kh = new KeyHolder();
		$().insert(entity.getClass(), entity, kh);
		return kh;
	}
	
	/**
	 * 根据主键更新对象，所以属性都参与更新。也可以使用主键ColumnIgnore来控制更新的时候忽略此字段
	 * @param entity
	 * @return
	 */
	default int updateById(T entity) {
		return $().updateById(entity);
	}
	/**
	 * 根据主键更新对象，只有不为null的属性参与更新
	 * @param entity
	 * @return
	 */
	default int updateTemplateById(T entity) {
		return $().updateTemplateById(entity);
	}
	
	/**
	 * 根据主键删除对象，如果对象是复合主键，传入对象本生即可
	 * @param key
	 * @return
	 */
	default void deleteById(Object key) {
		$().deleteById(this.getClass(), key);
	}
	
	/**
	 * 根据主键获取对象，如果对象不存在，则会抛出一个Runtime异常
	 * @param key
	 * @return
	 */
	default T unique(Object key) {
		return (T)$().unique(this.getClass(), key);
	}
	/**
	 * 根据主键获取对象，如果对象不存在，返回null
	 * @param key
	 * @return
	 */
	default T single(Object key) {
		return (T)$().single(this.getClass(), key);
	}
	
	/**
	 * 根据主键获取对象，如果在事物中执行会添加数据库行级锁(select * from table where id = ? for update)，如果对象不存在，返回null
	 * @param key
	 * @return
	 */
	default T lock(Object key) {
		return (T)$().lock(this.getClass(), key);
	}
	
	/**
	 * 返回实体对应的所有数据库记录
	 * @return
	 */
	default List<T> all() {
		return (List<T>)$().all(this.getClass());
	}
	/**
	 * 返回实体对应的一个范围的记录
	 * @param start
	 * @param size
	 * @return
	 */
	default List<T> all(int start,int size) {
		return (List<T>)$().all(this.getClass(), start, size);
	}
	
	/**
	 * 返回实体在数据库里的总数
	 * @return
	 */
	default long allCount() {
		return (long)$().allCount(this.getClass());
	}
	
	/**
	 * 模板查询，返回符合模板得所有结果。beetlsql将取出非null值（日期类型排除在外），从数据库找出完全匹配的结果集
	 * @param entity
	 * @return
	 */
	default List<T> template(T entity) {
		return (List<T>)$().template(entity);
	}

	/**
	 * 模板查询，返回一条结果,如果没有，返回null
	 * @param entity
	 * @return
	 */
	default T templateOne(T entity) {
		return (T)$().templateOne(entity);
	}

	default List<T> template(T entity,int start,int size) {
		return (List<T>)$().template(entity, start, size);
	}
	
	default void templatePage(String sqlId, PageQuery<T> query) {
		$().pageQuery(sqlId, (Class<T>)this.getClass(), query);
	}
	
	/**
	 * 符合模板得个数
	 * @param entity
	 * @return
	 */
	default long templateCount(T entity) {
		return $().templateCount(entity);
	}	
}
