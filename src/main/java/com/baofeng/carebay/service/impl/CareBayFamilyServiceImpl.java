package com.baofeng.carebay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.CareBayFamilyDAO;
import com.baofeng.carebay.entity.CareBayFamily;
import com.baofeng.carebay.service.ICareBayFamilyService;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("careBayFamilyService")
public class CareBayFamilyServiceImpl implements ICareBayFamilyService {

	@Autowired
	private CareBayFamilyDAO careBayFamilyDAO;

	public CareBayFamilyDAO getCareBayFamilyDAO() {
		return careBayFamilyDAO;
	}

	public void setCareBayFamilyDAO(CareBayFamilyDAO careBayFamilyDAO) {
		this.careBayFamilyDAO = careBayFamilyDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		return this.careBayFamilyDAO.readAllPages(pageSize, currentPage, filter);
	}

	/**
	 * 功能：添加亲属关系
	 * */
	@Override
	public boolean addCareBayFamily(CareBayFamily careBayFamily) {
		if (careBayFamily != null && careBayFamily.getId() != null && careBayFamily.getId().length() > 0) {
			CareBayFamily family = this.readCareBayFamily(careBayFamily.getId());
			if (family != null) {
				careBayFamily.setCreateDT(family.getCreateDT());
				careBayFamily.setStatus(family.getStatus());
			}
		}else{
			careBayFamily.setShare(Integer.valueOf(0));
		}
		return this.careBayFamilyDAO.saveCareBayFamily(careBayFamily);
	}

	/**
	 * 功能：读取亲属关系
	 * */
	@Override
	public CareBayFamily readCareBayFamily(String id) {
		return this.careBayFamilyDAO.readCareBayFamily(id);
	}

	/**
	 * 功能：删除亲属关系
	 * */
	@Override
	public boolean delCareBayFamily(String id) {
		return this.careBayFamilyDAO.deleteCareBayFamily(id);
	}
}
