package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.CurseCaseDetails;
import com.baofeng.utils.PageResult;

public interface ICurseCaseDetailsService {

	/** 分页数据查询 */
	@SuppressWarnings("rawtypes")
	PageResult readAllPages(Integer pageSize, Integer currentPage, String groupid);
	
	boolean addCurseCaseDetails(CurseCaseDetails details, String groupId);

	CurseCaseDetails readCurseCaseDetails(String id);

	boolean onLineCurseCaseDetails(String id);

	boolean delCurseCaseDetails(String id);

	boolean saveIndexs(String id1, String gid1, String indexs1);

	boolean addDetails(String id, String gid, String content, CurseCaseDetails details);

}
