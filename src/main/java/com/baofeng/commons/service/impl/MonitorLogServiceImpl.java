package com.baofeng.commons.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.commons.dao.MonitorLogDAO;
import com.baofeng.commons.entity.MonitorLog;
import com.baofeng.commons.entity.Operator;
import com.baofeng.commons.service.IMonitorLogService;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("monitorLogService")
public class MonitorLogServiceImpl implements IMonitorLogService {

	@Autowired
	private MonitorLogDAO monitorLogDAO;

	public MonitorLogDAO getMonitorLogDAO() {
		return monitorLogDAO;
	}

	public void setMonitorLogDAO(MonitorLogDAO monitorLogDAO) {
		this.monitorLogDAO = monitorLogDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {

		return null;
	}

	/** 添加操作日志 */
	@Override
	public boolean saveMonitorLog(Operator operator, String log) {
		if (StringUtils.isNotBlank(log)) {
			MonitorLog mlog = new MonitorLog();
			mlog.setOperator(operator);
			mlog.setLog(log);
			return this.monitorLogDAO.saveMonitorLog(mlog);
		}
		return false;
	}

}
