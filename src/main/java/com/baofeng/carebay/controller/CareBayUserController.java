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

import com.baofeng.carebay.entity.CareBayUser;
import com.baofeng.carebay.service.ICareBayUserService;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;
import com.baofeng.utils.SearchFilter;
import com.baofeng.utils.SearchRule;

@Controller
@RequestMapping("care")
public class CareBayUserController {

	@Autowired
	private ICareBayUserService careBayUserService;

	public ICareBayUserService getCareBayUserService() {
		return careBayUserService;
	}

	public void setCareBayUserService(ICareBayUserService careBayUserService) {
		this.careBayUserService = careBayUserService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/careBayUser");
		return mav;
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ModelAndView showChamberUser() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/chamberUser");
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows) {
		PageResult pages = this.careBayUserService.readAllPages(rows, page, null);
		return pages;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/readChamberPages", method = RequestMethod.POST)
	public PageResult readChamberPages(int page, int rows, HttpServletRequest request) {
		PageResult pages = null;
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null) {
			SearchFilter filter = new SearchFilter();
			if (user.getChamber() != null && user.getChamber().trim().length() > 0) {
				SearchRule search = new SearchRule();
				search.setData(user.getChamber());
				search.setField("chamber");
				search.setOp("eq");
				List<SearchRule> rules = new ArrayList<SearchRule>();
				rules.add(search);
				filter.setRules(rules);
			}
			pages = this.careBayUserService.readChamberPages(rows, page, filter);
		}
		return pages;
	}

	/**
	 * 功能：搜索用户
	 * */
	@ResponseBody
	@RequestMapping(value = "/readPagesSkip", method = RequestMethod.POST)
	public PageResult readPagesSkip(int page, int rows, String filter, HttpServletRequest request) {
		PageResult pages = null;
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null) {
			SearchFilter $filter = new SearchFilter();
			if (user.getChamber() != null && user.getChamber().trim().length() > 0) {
				SearchRule search = new SearchRule();
				search.setData(user.getChamber());
				search.setField("operator.chamber");
				search.setOp("eq");
				List<SearchRule> rules = new ArrayList<SearchRule>();
				rules.add(search);
				$filter.setRules(rules);
			}
			pages = this.careBayUserService.readChamberPages(rows, page, filter, $filter);
		}
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(String id, String room, String name, String birthDate, String homeAddress, String phone, String emContact, String emPhone, String checkInDT,
			String checkOutDT, String chamber, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/chamberUser");
		Operator operator = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (operator != null) {
			if (this.careBayUserService.addChamberUser(id, room, name, birthDate, homeAddress, phone, emContact, emPhone, checkInDT, checkOutDT, chamber, operator)) {
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
	public CareBayUser read(String id) {
		CareBayUser chamber = this.careBayUserService.readChamberUser(id);
		if (chamber != null) {
			return chamber;
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
		if (this.careBayUserService.deleteChamberUser(id)) {
			msg.setResultStatus(200);
			msg.setResultMessage("success");
		}
		return msg;
	}

}
