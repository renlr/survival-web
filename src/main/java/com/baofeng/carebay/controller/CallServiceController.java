package com.baofeng.carebay.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.carebay.entity.ButlerService;
import com.baofeng.carebay.entity.MonthsMealOrder;
import com.baofeng.carebay.service.ICallService;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;
import com.baofeng.utils.SearchFilter;
import com.baofeng.utils.SearchRule;

@Controller
@RequestMapping("call")
public class CallServiceController {

	@Autowired
	private ICallService callService;

	public ICallService getCallService() {
		return callService;
	}

	public void setCallService(ICallService callService) {
		this.callService = callService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/callService");
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
				search.setField("user.chamber");
				search.setOp("eq");
				List<SearchRule> rules = new ArrayList<SearchRule>();
				rules.add(search);
				filter.setRules(rules);
			}
			pages = this.callService.readAllPages(rows, page, filter);
		}
		return pages;
	}

	/**
	 * 功能:读取服务
	 * */
	@ResponseBody
	@RequestMapping(value = "/readServiceCall", method = RequestMethod.GET)
	public ButlerService readServiceCall(HttpServletRequest request) {
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null) {
			ButlerService service = this.callService.readButlerService(user);
			if (service != null) {
				return service;
			}
		}
		return null;
	}

	/**
	 * 功能:读取点餐服务
	 * */
	@ResponseBody
	@RequestMapping(value = "/readServiceMeal", method = RequestMethod.GET)
	public MonthsMealOrder readServiceMeal(HttpServletRequest request) {
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null) {
			MonthsMealOrder service = this.callService.readServiceMonthsMealOrders(user);
			if (service != null) {
				return service;
			}
		}
		return null;
	}

	/**
	 * 功能:现在呼叫服务
	 * */
	@ResponseBody
	@RequestMapping(value = "/serviceCall", method = RequestMethod.GET)
	public ResultMsg serviceCall(String id, HttpServletRequest request) {
		ResultMsg msg = new ResultMsg();
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null && id != null) {
			if (this.callService.serviceing(id, user)) {
				msg.setResultMessage("success");
			}
		}
		return msg;
	}

	/**
	 * 功能:现在点餐服务
	 * */
	@ResponseBody
	@RequestMapping(value = "/serviceMeal", method = RequestMethod.GET)
	public ResultMsg serviceMeal(String id, HttpServletRequest request) {
		ResultMsg msg = new ResultMsg();
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null && id != null) {
			if (this.callService.updateServiceMeal(id, user)) {
				msg.setResultMessage("success");
			}
		}
		return msg;
	}

}
