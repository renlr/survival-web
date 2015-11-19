package com.baofeng.carebay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.WeekServiceDAO;
import com.baofeng.carebay.entity.WeekService;
import com.baofeng.carebay.service.IWeekServiceService;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("weekServiceService")
public class WeekServiceServiceImpl implements IWeekServiceService {

	@Autowired
	private WeekServiceDAO weekServiceDAO;

	public WeekServiceDAO getWeekServiceDAO() {
		return weekServiceDAO;
	}

	public void setWeekServiceDAO(WeekServiceDAO weekServiceDAO) {
		this.weekServiceDAO = weekServiceDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		return this.weekServiceDAO.readAllPages(pageSize, currentPage, filter);
	}

	@Override
	public boolean addWeekService(String id, String name) {
		WeekService week = new WeekService();
		week.setName(Integer.valueOf(name));
		week.setOnline(Integer.valueOf(0));
		return this.weekServiceDAO.saveWeekService(week);
	}

	@Override
	public boolean readWeekServiceByName(String name) {
		WeekService week = this.weekServiceDAO.readWeekServiceByName(name);
		if (week != null) {
			return true;
		}
		return false;
	}

	@Override
	public WeekService readWeekService(String id) {
		return this.weekServiceDAO.readWeekService(id);
	}
	
	@Override
	public boolean onLineWeekService(String id) {
		WeekService week = this.weekServiceDAO.readWeekService(id);
		if (week != null) {
			if (week.getOnline() == null)
				week.setOnline(Integer.valueOf(0));
			if (week.getOnline().intValue() == Integer.valueOf(0).intValue()) {
				week.setOnline(Integer.valueOf(1));
			} else {
				week.setOnline(Integer.valueOf(0));
			}
			this.weekServiceDAO.saveWeekService(week);
			return true;
		}
		return false;
	}

}
