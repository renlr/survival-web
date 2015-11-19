package com.baofeng.commons.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.commons.entity.MonitorLog;
import com.baofeng.utils.IBaseDAO;

@Repository("monitorLogDAO")
public class MonitorLogDAO {

	@Autowired
	private IBaseDAO baseDAO;

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public boolean saveMonitorLog(MonitorLog mlog) {
		try {
			if (mlog != null && mlog.getId() != null && mlog.getId().trim().length() > 0)
				this.baseDAO.mrege(mlog);
			else
				this.baseDAO.save(mlog);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
