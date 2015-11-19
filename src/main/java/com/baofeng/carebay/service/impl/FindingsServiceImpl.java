package com.baofeng.carebay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.FindingsDAO;
import com.baofeng.carebay.service.IFindingsService;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("findingsService")
public class FindingsServiceImpl implements IFindingsService {

	@Autowired
	private FindingsDAO findingsDAO;

	public FindingsDAO getFindingsDAO() {
		return findingsDAO;
	}

	public void setFindingsDAO(FindingsDAO findingsDAO) {
		this.findingsDAO = findingsDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {

		return null;
	}

}
