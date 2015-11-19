package com.baofeng.carebay.controller;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.carebay.entity.GestationTips;
import com.baofeng.carebay.entity.WeekService;
import com.baofeng.carebay.service.IGestationTipsService;
import com.baofeng.carebay.service.IWeekServiceService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;

@Controller
@RequestMapping("tips")
public class GestationTipsController {

	@Autowired
	private IWeekServiceService weekServiceService;
	@Autowired
	private IGestationTipsService gestationTipsService;

	public IWeekServiceService getWeekServiceService() {
		return weekServiceService;
	}

	public void setWeekServiceService(IWeekServiceService weekServiceService) {
		this.weekServiceService = weekServiceService;
	}

	public IGestationTipsService getGestationTipsService() {
		return gestationTipsService;
	}

	public void setGestationTipsService(IGestationTipsService gestationTipsService) {
		this.gestationTipsService = gestationTipsService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/gestationTips");
		return mav;
	}

	@RequestMapping(value = "/editContent", method = RequestMethod.GET)
	public ModelAndView editContent(String id, String gid) {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/gestationTipsContent");
		GestationTips details = this.gestationTipsService.readGestationTips(id);
		WeekService week = this.weekServiceService.readWeekService(gid);
		mav.addObject("details", details);
		mav.addObject("week", week);
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows, String gid) {
		PageResult pages = this.gestationTipsService.readAllPages(rows, page, gid);
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResultMsg save(String id, String gid, String name) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		GestationTips tips = new GestationTips();
		tips.setName(name);
		tips.setId(id);
		if (this.gestationTipsService.addGestationTips(tips, gid)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：编辑内容
	 * */
	@ResponseBody
	@RequestMapping(value = "/saveContent", method = RequestMethod.POST)
	public ResultMsg saveContent(String id, String content) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		StringBuffer $temp = new StringBuffer();
		$temp.append(Constants.HTMLCENTERCODE);
		GestationTips details = new GestationTips();
		if (content != null && content.trim().length() > 0) {
			File file = new File(Constants.DEFAULT_BUILDHTMLPATH + File.separator + id + ".html");
			if(file.exists())
				file.delete();
			$temp.append(content);
			FileUtils.writeStringToFile(file, $temp.toString(), "utf-8");
			details.setContent(id + ".html");
		}
		if (this.gestationTipsService.addTips(id, content)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public GestationTips edit(String id) {
		GestationTips tips = this.gestationTipsService.readGestationTips(id);
		if (tips != null) {
			return tips;
		}
		return null;
	}

	/**
	 * 功能：上下线
	 * */
	@ResponseBody
	@RequestMapping(value = "/online", method = RequestMethod.GET)
	public ResultMsg online(String id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.gestationTipsService.onLineTips(id)) {
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
		if (this.gestationTipsService.delGestationTips(id)) {
			result.setResultMessage("success");
		}
		return result;
	}

}
