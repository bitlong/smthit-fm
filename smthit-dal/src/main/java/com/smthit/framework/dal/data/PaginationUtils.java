/**
 * 
 */
package com.smthit.framework.dal.data;

import org.springframework.data.domain.Page;


/**
 * @author Bean
 *
 */
public class PaginationUtils {

	/**
	 * 
	 */
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
}
