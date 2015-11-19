package com.baofeng.carebay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.GrowupDAO;
import com.baofeng.carebay.entity.Growup;
import com.baofeng.carebay.service.IGrowupService;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("growupService")
public class GrowupServiceImpl implements IGrowupService {

	@Autowired
	private GrowupDAO growupDAO;

	public GrowupDAO getGrowupDAO() {
		return growupDAO;
	}

	public void setGrowupDAO(GrowupDAO growupDAO) {
		this.growupDAO = growupDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		return this.growupDAO.readAllPages(pageSize, currentPage, filter);
	}

	@Override
	public boolean addGrowup(Growup growup) {
		if (growup != null && growup.getId() != null && growup.getId().trim().length() > 0) {
			Growup $growup = this.readGrowup(growup.getId());
			if ($growup != null) {
				$growup.setName(growup.getName());
				growup = $growup;
			}
		}
		return this.growupDAO.saveGrowup(growup);
	}

	@Override
	public Growup readGrowup(String id) {
		return this.growupDAO.readGrowup(id);
	}

	@Override
	public boolean delGrowup(String id) {
		Growup growup = this.readGrowup(id);
		if (growup != null) {
			growup.setStatus(EntityStatus.DELETED);
			this.growupDAO.saveGrowup(growup);
			return true;
		}
		return false;
	}

	@Override
	public boolean readGrowupByName(String name) {
		return this.growupDAO.readGrowupByName(name);
	}
}
