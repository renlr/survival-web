package com.baofeng.carebay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.carebay.entity.Growup;
import com.baofeng.carebay.service.IGrowupService;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;

@Controller
@RequestMapping("growup")
public class GrowupController {

	@Autowired
	private IGrowupService growupService;

	public IGrowupService getGrowupService() {
		return growupService;
	}

	public void setGrowupService(IGrowupService growupService) {
		this.growupService = growupService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/growup");
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows) {
		PageResult pages = this.growupService.readAllPages(rows, page, null);
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResultMsg save(Growup growup) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.growupService.addGrowup(growup)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public Growup edit(String id) {
		Growup growup = this.growupService.readGrowup(id);
		if (growup != null) {
			return growup;
		}
		return null;
	}

	/**
	 * 功能：验证
	 * */
	@ResponseBody
	@RequestMapping(value = "/isVildateUrl", method = RequestMethod.GET)
	public ResultMsg isVildateUrl(String name) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.growupService.readGrowupByName(name)) {
			result.setResultStatus(200);
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：删除
	 * */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ResultMsg delete(String id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.growupService.delGrowup(id)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：添加业务详细内容
	 * */
	@RequestMapping(value = "/addGrowupIndex", method = RequestMethod.GET)
	public ModelAndView addMusic(String id) {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/growupIndex");
		Growup growup = this.growupService.readGrowup(id);
		if (growup != null) {
			mav.addObject("growup", growup);
		}
		return mav;
	}
}
