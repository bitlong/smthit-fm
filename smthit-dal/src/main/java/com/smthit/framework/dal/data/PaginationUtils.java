/**
 * 
 */
package com.smthit.framework.dal.data;

import org.beetl.sql.core.engine.PageQuery;
import org.springframework.data.domain.Page;

import com.smthit.lang.convert.AbstractConvert;


/**
 * @author Bean
 *
 */
public class PaginationUtils {

	private PaginationUtils() {
	}

	public static <VO, PO> Pagination<VO> convertPagination(Page<PO> page) {
		return new Pagination<VO>()
				.setCurrentPage(page.getNumber())
				.setTotal(page.getTotalElements())
				.setTotalPages(page.getTotalPages());	
	}
	
	public static <VO, PO> Pagination<VO> convertPagination(Pagination<PO> page) {
		return new Pagination<VO>()
				.setCurrentPage(page.getCurrentPage())
				.setTotal(page.getTotal())
				.setTotalPages(page.getTotalPages());	
	}
	
	public static <VO, PO> Pagination<VO> convertPagination(PageQuery<PO> page, AbstractConvert<PO, VO> convert) {
		return new Pagination<VO>()
				.setCurrentPage((int)page.getPageNumber())
				.setTotal(page.getTotalRow())
				.setTotalPages((int)page.getTotalPage())
				.setRows(convert.toVOs(page.getList()));			
	}
	
	public static <VO, PO> Pagination<VO> convertPagination(Page<PO> page, AbstractConvert<PO, VO> convert) {
		return new Pagination<VO>()
				.setCurrentPage(page.getNumber())
				.setTotal(page.getTotalElements())
				.setTotalPages(page.getTotalPages())
				.setRows(convert.toVOs(page.getContent()));
	}
	
	public static <VO, PO> Pagination<VO> convertPagination(Pagination<PO> page, AbstractConvert<PO, VO> convert) {
		return new Pagination<VO>()
				.setCurrentPage(page.getCurrentPage())
				.setTotal(page.getTotal())
				.setTotalPages(page.getTotalPages())
				.setRows(convert.toVOs(page.getRows()));
	}
}
