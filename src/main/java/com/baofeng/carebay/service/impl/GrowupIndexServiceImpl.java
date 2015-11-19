package com.baofeng.carebay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.GrowupDAO;
import com.baofeng.carebay.dao.GrowupIndexDAO;
import com.baofeng.carebay.entity.Growup;
import com.baofeng.carebay.entity.GrowupIndex;
import com.baofeng.carebay.service.IGrowupIndexService;
import com.baofeng.utils.PageResult;

@Service("growupIndexService")
public class GrowupIndexServiceImpl implements IGrowupIndexService {

	@Autowired
	private GrowupDAO growupDAO;
	@Autowired
	private GrowupIndexDAO growupIndexDAO;

	public GrowupIndexDAO getGrowupIndexDAO() {
		return growupIndexDAO;
	}

	public void setGrowupIndexDAO(GrowupIndexDAO growupIndexDAO) {
		this.growupIndexDAO = growupIndexDAO;
	}

	public GrowupDAO getGrowupDAO() {
		return growupDAO;
	}

	public void setGrowupDAO(GrowupDAO growupDAO) {
		this.growupDAO = growupDAO;
	}

	@Override
	public PageResult readAllPages(int rows, int page, String groupId) {
		return this.growupIndexDAO.readAllPages(rows, page, groupId);
	}

	@Override
	public boolean delGrowupIndex(String id) {
		return this.growupIndexDAO.deleteGrowupIndex(id);
	}

	@Override
	public GrowupIndex readGrowupIndex(String id) {
		return this.growupIndexDAO.readGrowupIndex(id);
	}

	@Override
	public boolean addGrowupIndex(GrowupIndex gid,String groupId) {
		Growup growup = this.growupDAO.readGrowup(groupId);
		if(growup != null){
			gid.setGrowup(growup);
		}
		return this.growupIndexDAO.addGrowupIndex(gid);
	}

}
