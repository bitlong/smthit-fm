/**
 * 
 */
package com.smthit.framework.dal.data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.smthit.framework.dal.exception.PropertyNotFoundException;
import com.smthit.lang.utils.ReflectUtils;
import com.smthit.lang2.utils.ClassKit;
import com.smthit.lang2.utils.Property;

import lombok.Data;

/**
 * @author Bean
 *
 */
@Data
@lombok.experimental.Accessors(chain = true)
public class PageParam<C> {
	private int pageNumber;
	private int pageSize;
	
	private Map<String, Object> params;
	private Class<C> cls;
	
	private List<OrderBy> order;
	
	private String orderBy;
	
	public PageParam(Class<C> cls) {
		this.cls = cls;
		this.pageNumber = 1;
		this.pageSize = 30;
		this.params = new HashMap<String, Object>();
		this.order = new ArrayList<OrderBy>();
	}
	
	public PageParam(Class<C> cls, int pageNumber, int pageSize) {
		this.cls = cls;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.params = new HashMap<String, Object>();
		this.order = new ArrayList<OrderBy>();
	}
	
	public <T, R> PageParam<C> setParam(Property<T, R> property, Object value) {
		String fieldName = ClassKit.getFieldName(property);
		
		if(checkProperty(fieldName)) {
			return setParam(fieldName, value);
		} else {
			throw new PropertyNotFoundException(String.format("%s 属性不存在", fieldName));
		}
	}
	
	public <T, R> PageParam<C> setParam(Property<T, R> property, Object min, Object max) {
		String fieldName = ClassKit.getFieldName(property);
		
		if(checkProperty(fieldName)) {
			return setParam(fieldName, min);
		} else {
			throw new PropertyNotFoundException(String.format("%s 属性不存在", fieldName));
		}
	}
	
	public <T, R> PageParam<C> addOrder(Property<T, R> property, EnumOrder order) {
		String fieldName = ClassKit.getFieldName(property);
		
		if(checkProperty(fieldName)) {
			OrderBy orderBy = new OrderBy(fieldName, order);
			this.order.add(orderBy);
			return this;
		} else {
			throw new PropertyNotFoundException(String.format("%s 属性不存在", fieldName));
		}
	}
	
	public String getOrderBy() {
		if(this.order != null && this.order.size() > 0) {
			StringBuilder sb = new StringBuilder();
			this.order.forEach((o) -> {
				sb.append(o.getField() + " " + o.getOrder().getValue()).append(",");
			});
			
			sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		} else {
			return this.orderBy;
		}
	}
	
	private boolean checkProperty(String fieldName) {
		Field field = ReflectUtils.getFields(cls, fieldName);
		if(field != null)
			return true;
		
		return false;
	}
	
	private PageParam<C> setParam(String key, Object value) {
		if(params == null) {
			params = new HashMap<>(10);
		}
		params.put(key, value);
		
		return this;
	}
}
