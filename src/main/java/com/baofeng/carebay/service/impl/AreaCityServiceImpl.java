package com.baofeng.carebay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.AreaCityDAO;
import com.baofeng.carebay.service.IAreaCityService;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("areaCityService")
public class AreaCityServiceImpl implements IAreaCityService {

	@Autowired
	private AreaCityDAO areaCityDAO;

	public AreaCityDAO getAreaCityDAO() {
		return areaCityDAO;
	}

	public void setAreaCityDAO(AreaCityDAO areaCityDAO) {
		this.areaCityDAO = areaCityDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		return this.areaCityDAO.readAllPages(pageSize, currentPage, filter);
	}

}
