package com.baofeng.carebay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.carebay.service.IAreaCityService;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;

@Controller
@RequestMapping("area")
public class AreaCityController {

	@Autowired
	private IAreaCityService areaCityService;

	public IAreaCityService getAreaCityService() {
		return areaCityService;
	}

	public void setAreaCityService(IAreaCityService areaCityService) {
		this.areaCityService = areaCityService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/areaCity");
		return mav;
	}
	
	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows) {
		PageResult pages = this.areaCityService.readAllPages(rows, page, null);
		return pages;
	}
}
