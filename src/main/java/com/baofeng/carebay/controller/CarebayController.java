package com.baofeng.carebay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.carebay.service.ICarebayService;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;

@Controller
@RequestMapping("carebay")
public class CarebayController {

	@Autowired
	private ICarebayService carebayService;

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/carebay");
		return mav;
	}
	
	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows) {
		PageResult pages = this.carebayService.readAllPages(rows, page, null);
		return pages;
	}
	
	public ICarebayService getCarebayService() {
		return carebayService;
	}

	public void setCarebayService(ICarebayService carebayService) {
		this.carebayService = carebayService;
	}
}
