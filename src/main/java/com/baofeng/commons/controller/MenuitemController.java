package com.baofeng.commons.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.commons.entity.Operator;
import com.baofeng.commons.service.IMenuitemService;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;

@Controller
@RequestMapping("/menu")
public class MenuitemController {

	@Autowired
	private IMenuitemService menuitemService;

	public IMenuitemService getMenuitemService() {
		return menuitemService;
	}

	public void setMenuitemService(IMenuitemService menuitemService) {
		this.menuitemService = menuitemService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView("menuitem");
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows,HttpServletRequest request) {
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null) {
			PageResult pages = this.menuitemService.readAllPages(rows, page, user);
			return pages;
		}
		return null;
	}
	
	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public ResultMsg init() {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if(this.menuitemService.init()){
			result.setResultMessage("success");
			result.setResultStatus(Integer.valueOf(200));
		}
		return result;
	}
}
