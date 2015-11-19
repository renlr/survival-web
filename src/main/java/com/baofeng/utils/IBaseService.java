package com.baofeng.utils;


public interface IBaseService {

	

	/** 分页数据查询 */
	@SuppressWarnings("rawtypes")
	PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter);
}
