package com.baofeng.carebay.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.carebay.entity.Activity;
import com.baofeng.carebay.service.IActivityService;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;
import com.baofeng.utils.SearchFilter;
import com.baofeng.utils.SearchRule;

@Controller
@RequestMapping("activity")
public class ActivityController {

	@Autowired
	private IActivityService activityService;

	public IActivityService getActivityService() {
		return activityService;
	}

	public void setActivityService(IActivityService activityService) {
		this.activityService = activityService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/activity");
		return mav;
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(String gid) {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/activityDetails");
		mav.addObject("gid", gid);
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows, HttpServletRequest request) {
		PageResult pages = null;
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null) {
			SearchFilter filter = new SearchFilter();
			if (user.getChamber() != null && user.getChamber().trim().length() > 0) {
				SearchRule search = new SearchRule();
				search.setData(user.getChamber());
				search.setField("operator.chamber");
				search.setOp("eq");
				List<SearchRule> rules = new ArrayList<SearchRule>();
				rules.add(search);
				filter.setRules(rules);
			}
			pages = this.activityService.readAllPages(rows, page, filter);
		}
		return pages;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/readPagesDetails", method = RequestMethod.POST)
	public PageResult readPagesDetails(int page, int rows,String gid) {
		PageResult pages = this.activityService.readAllPagesDetails(rows, page,gid);
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(String id, String name, String content, String details, String beginTime, String endTime, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/activity");
		Operator operator = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (operator != null) {
			Activity act = new Activity();
			act.setId(id);
			act.setName(name);
			act.setContent(content);
			act.setDetails(details);
			act.setOperator(operator);
			act.setBeginTime(DateUtils.parseDate(beginTime, new String[] { Constants.format8 }));
			act.setEndTime(DateUtils.parseDate(endTime, new String[] { Constants.format8 }));
			if (this.activityService.addActivity(act)) {
				mav.addObject("result", "success");
			}
		}
		return mav;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public Activity read(String id) {
		Activity act = this.activityService.readActivity(id);
		if (act != null) {
			return act;
		}
		return null;
	}

	/**
	 * 功能：删除
	 * */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ResultMsg delete(String id) {
		ResultMsg msg = new ResultMsg();
		msg.setResultMessage("errors");
		if (this.activityService.deleteActivity(id)) {
			msg.setResultStatus(200);
			msg.setResultMessage("success");
		}
		return msg;
	}

	/**
	 * 功能：上下线
	 * */
	@ResponseBody
	@RequestMapping(value = "/online", method = RequestMethod.GET)
	public ResultMsg online(String id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.activityService.onLineActivity(id)) {
			result.setResultStatus(200);
			result.setResultMessage("success");
		}
		return result;
	}
	
	/**
	 * 功能：定时任务
	 * */
	@ResponseBody
	@RequestMapping(value = "/saveLineTask", method = RequestMethod.POST)
	public ResultMsg saveLineTask(String ids, String onlineDT, String offlineDT) throws Exception {
		ResultMsg result = new ResultMsg();
		if (this.activityService.saveOnLineTask(ids, onlineDT, offlineDT)) {
			result.setResultMessage("success");
		}
		return result;
	}
	
}
