package com.baofeng.carebay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.carebay.entity.WeekService;
import com.baofeng.carebay.service.IWeekServiceService;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;

@Controller
@RequestMapping("week")
public class WeekServiceController {

	@Autowired
	private IWeekServiceService weekServiceService;

	public IWeekServiceService getWeekServiceService() {
		return weekServiceService;
	}

	public void setWeekServiceService(IWeekServiceService weekServiceService) {
		this.weekServiceService = weekServiceService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/weekService");
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows) {
		PageResult pages = this.weekServiceService.readAllPages(rows, page, null);
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResultMsg save(String id, String name) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.weekServiceService.addWeekService(id, name)) {
			result.setResultMessage("success");
		}
		return result;
	}
	
	/**
	 * 功能：验证
	 * */
	@ResponseBody
	@RequestMapping(value = "/vaildation", method = RequestMethod.GET)
	public ResultMsg vaildation(String name) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.weekServiceService.readWeekServiceByName(name)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：上下线
	 * */
	@ResponseBody
	@RequestMapping(value = "/online", method = RequestMethod.GET)
	public ResultMsg online(String id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.weekServiceService.onLineWeekService(id)) {
			result.setResultStatus(200);
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：首页轮播
	 * */
	@RequestMapping(value = "/addPoster", method = RequestMethod.GET)
	public ModelAndView addPoster(String id) {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/poster");
		WeekService week = this.weekServiceService.readWeekService(id);
		if (week != null) {
			mav.addObject("week", week);
		}
		return mav;
	}

	/**
	 * 功能：孕期贴士
	 * */
	@RequestMapping(value = "/addTips", method = RequestMethod.GET)
	public ModelAndView addTips(String id) {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/gestationTips");
		WeekService week = this.weekServiceService.readWeekService(id);
		if (week != null) {
			mav.addObject("week", week);
		}
		return mav;
	}

	/**
	 * 功能：胎教音乐
	 * */
	@RequestMapping(value = "/addMusic", method = RequestMethod.GET)
	public ModelAndView addMusic(String id) {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/music");
		WeekService week = this.weekServiceService.readWeekService(id);
		if (week != null) {
			mav.addObject("week", week);
		}
		return mav;
	}

	/**
	 * 功能：胎教音乐背景
	 * */
	@RequestMapping(value = "/addMusicbg", method = RequestMethod.GET)
	public ModelAndView addMusicbg(String id) {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/musicbg");
		WeekService week = this.weekServiceService.readWeekService(id);
		if (week != null) {
			mav.addObject("week", week);
		}
		return mav;
	}

}
