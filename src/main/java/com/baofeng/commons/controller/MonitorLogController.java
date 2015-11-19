package com.baofeng.commons.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.commons.service.IMonitorLogService;

@Controller
@RequestMapping("/mlog")
public class MonitorLogController {

	@Autowired
	private IMonitorLogService monitorLogService;

	public IMonitorLogService getMonitorLogService() {
		return monitorLogService;
	}

	public void setMonitorLogService(IMonitorLogService monitorLogService) {
		this.monitorLogService = monitorLogService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView("page/monitorLog");
		return mav;
	}
}
