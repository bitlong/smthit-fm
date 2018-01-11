/**
 * 
 */
package com.smthit.framework.mvc.controller;

import org.springframework.data.domain.Sort;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smthit.framework.dal.criteria.Restriction;
import com.smthit.framework.dal.data.Pagination;

/**
 * @author Bean
 *
 */
public class AbstractListController<T> extends AbstractController {
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
    }
	
    @ModelAttribute
    public FilterCondition createFilterCondition() {
        return new FilterCondition();
    }
    
    /**
     * 带过滤条件的模版方法
     * @param filterCondition
     * @param page
     * @param rows
     * @return
     */
	public @ResponseBody Pagination<T> listByJson(final @ModelAttribute FilterCondition filterCondition, 
			@RequestParam(defaultValue="1") int page,
			@RequestParam(defaultValue="20") int rows) {
		return new Pagination<T>();
	}
	
	/**
	 * 不带条件的模板方法
	 * @param page
	 * @param rows
	 * @return
	 */
	public @ResponseBody Pagination<T> listByJson(@RequestParam(defaultValue="1") int page,
			@RequestParam(defaultValue="20") int rows) {
		return new Pagination<T>();
	}
		
	public Restriction<T> createRestriction(int page, int rows, Sort sort) {
		return new Restriction<T>(createPageRequest(page, rows, sort));
	}
}
