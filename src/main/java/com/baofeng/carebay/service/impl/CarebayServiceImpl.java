package com.baofeng.carebay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.CarebayDAO;
import com.baofeng.carebay.service.ICarebayService;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("carebayService")
public class CarebayServiceImpl implements ICarebayService {

	@Autowired
	private CarebayDAO carebayDAO;

	public CarebayDAO getCarebayDAO() {
		return carebayDAO;
	}

	public void setCarebayDAO(CarebayDAO carebayDAO) {
		this.carebayDAO = carebayDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		return this.carebayDAO.readAllPages(pageSize, currentPage, filter);
	}

}
