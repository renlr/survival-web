package com.baofeng.carebay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.carebay.entity.RecductList;
import com.baofeng.carebay.service.IRecductListService;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;

/**
 * 功能：产品分类
 * */
@Controller
@RequestMapping("ductcy")
public class RecductListController {

	@Autowired
	private IRecductListService recductListService;

	public IRecductListService getRecductListService() {
		return recductListService;
	}

	public void setRecductListService(IRecductListService recductListService) {
		this.recductListService = recductListService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/recductList");
		return mav;
	}
	
	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows) {
		PageResult pages = this.recductListService.readAllPages(rows, page, null);
		return pages;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/readPagesDetails", method = RequestMethod.POST)
	public PageResult readPagesDetails(int page, int rows, String gid) {
		PageResult pages = this.recductListService.readPagesDetails(rows, page, gid);
		return pages;
	}

	/**
	 * 功能：验证
	 * */
	@ResponseBody
	@RequestMapping(value = "/vildate", method = RequestMethod.GET)
	public ResultMsg isVildateUrl(String name) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.recductListService.readProductCatByName(name)) {
			result.setResultStatus(200);
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：添加修改
	 * */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResultMsg save(String id, String name) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		RecductList category = new RecductList();
		category.setId(id);
		category.setName(name);
		if (this.recductListService.addProductCat(category)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：添加分类产品
	 * */
	@ResponseBody
	@RequestMapping(value = "/addDetails", method = RequestMethod.POST)
	public ResultMsg addDetails(String gid, String ids) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.recductListService.addProductCatDetails(gid, ids)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public RecductList edit(String id) {
		RecductList category = this.recductListService.readProductCat(id);
		if (category != null) {
			return category;
		}
		return null;
	}

	/**
	 * 功能：删除
	 * */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ResultMsg delete(String id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.recductListService.deleteProductCat(id)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：删除
	 * */
	@ResponseBody
	@RequestMapping(value = "/deleteDetails", method = RequestMethod.GET)
	public ResultMsg deleteDetails(String gid, String ids) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.recductListService.deleteProductCatDetails(gid, ids)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：添加业务详细内容
	 * */
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView addMusic(String id) {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/productCatDetails");
		RecductList category = this.recductListService.readProductCat(id);
		if (category != null) {
			mav.addObject("cat", category);
		}
		return mav;
	}
}
