package com.baofeng.carebay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.GestationTipsDAO;
import com.baofeng.carebay.dao.WeekServiceDAO;
import com.baofeng.carebay.entity.GestationTips;
import com.baofeng.carebay.entity.WeekService;
import com.baofeng.carebay.service.IGestationTipsService;
import com.baofeng.utils.PageResult;

@Service("gestationTipsService")
public class GestationTipsServiceImpl implements IGestationTipsService {

	@Autowired
	private WeekServiceDAO weekServiceDAO;
	@Autowired
	private GestationTipsDAO gestationTipsDAO;

	public WeekServiceDAO getWeekServiceDAO() {
		return weekServiceDAO;
	}

	public void setWeekServiceDAO(WeekServiceDAO weekServiceDAO) {
		this.weekServiceDAO = weekServiceDAO;
	}

	public GestationTipsDAO getGestationTipsDAO() {
		return gestationTipsDAO;
	}

	public void setGestationTipsDAO(GestationTipsDAO gestationTipsDAO) {
		this.gestationTipsDAO = gestationTipsDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, String gid) {
		return this.gestationTipsDAO.readAllPages(pageSize, currentPage, gid);
	}

	@Override
	public boolean addGestationTips(GestationTips tips, String gid) {
		if (tips.getId() != null && tips.getId().trim().length() > 0) {
			GestationTips $tips = this.gestationTipsDAO.readGestationTips(tips.getId());
			if ($tips != null) {
				$tips.setName(tips.getName());
				tips = $tips;
			}
		}
		WeekService week = this.weekServiceDAO.readWeekService(gid);
		if (week != null) {
			tips.setWeek(week);
		}
		return this.gestationTipsDAO.saveGestationTips(tips);
	}

	@Override
	public GestationTips readGestationTips(String id) {
		return this.gestationTipsDAO.readGestationTips(id);
	}

	@Override
	public boolean delGestationTips(String id) {
		return this.gestationTipsDAO.delGestationTips(id);
	}

	@Override
	public boolean addTips(String id, String content) {
		GestationTips tips = this.readGestationTips(id);
		if (tips != null) {
			tips.setContent(id + ".html");
			this.gestationTipsDAO.saveGestationTips(tips);
			return true;
		}
		return false;
	}

	@Override
	public boolean onLineTips(String id) {
		GestationTips tips = this.readGestationTips(id);
		if (tips != null) {
			if (tips.getOnline() == null) {
				tips.setOnline(Integer.valueOf(0));
			}
			if (tips.getOnline().intValue() == Integer.valueOf(0).intValue()) {
				tips.setOnline(Integer.valueOf(1));
			}else{
				tips.setOnline(Integer.valueOf(0));
			}
			this.gestationTipsDAO.saveGestationTips(tips);
			return true;
		}
		return false;
	}

}
