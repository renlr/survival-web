package com.baofeng.commons.service;

import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.IBaseService;

public interface IMonitorLogService extends IBaseService {

	/** 记录系统操作日志 */
	boolean saveMonitorLog(Operator operator, String log);
}
