package com.baofeng.carebay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.carebay.entity.CareBayFamily;
import com.baofeng.carebay.service.ICareBayFamilyService;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;

@Controller
@RequestMapping("/family")
public class CareBayFamilyController {

	@Autowired
	private ICareBayFamilyService careBayFamilyService;

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/careBayFamily");
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows) {
		PageResult pages = this.careBayFamilyService.readAllPages(rows, page, null);
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(CareBayFamily careBayFamily) throws Exception {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/careBayFamily");
		this.careBayFamilyService.addCareBayFamily(careBayFamily);
		return mav;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public CareBayFamily edit(String id) {
		CareBayFamily family = this.careBayFamilyService.readCareBayFamily(id);
		if (family != null) {
			return family;
		}
		return null;
	}

	/**
	 * 功能：删除
	 * */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(String id) {
		if (this.careBayFamilyService.delCareBayFamily(id)) {
			return "success";
		}
		return "error";
	}
	
	public ICareBayFamilyService getCareBayFamilyService() {
		return careBayFamilyService;
	}

	public void setCareBayFamilyService(ICareBayFamilyService careBayFamilyService) {
		this.careBayFamilyService = careBayFamilyService;
	}
}
