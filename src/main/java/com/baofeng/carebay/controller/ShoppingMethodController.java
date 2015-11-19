package com.baofeng.carebay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.carebay.entity.ShoppingMethod;
import com.baofeng.carebay.service.IShoppingMethodService;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;

/**
 * 功能：配送方式
 * */
@Controller
@RequestMapping("smethod")
public class ShoppingMethodController {

	@Autowired
	private IShoppingMethodService shoppingMethodService;

	public IShoppingMethodService getShoppingMethodService() {
		return shoppingMethodService;
	}

	public void setShoppingMethodService(IShoppingMethodService shoppingMethodService) {
		this.shoppingMethodService = shoppingMethodService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/shoppingMethod");
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows) {
		PageResult pages = this.shoppingMethodService.readAllPages(rows, page, null);
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResultMsg save(String id, String method) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		ShoppingMethod $method = new ShoppingMethod();
		$method.setId(id);
		$method.setMethod(method);
		if (this.shoppingMethodService.addShoppingMedthod($method)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ShoppingMethod edit(String id) {
		ShoppingMethod method = this.shoppingMethodService.readShoppingMethod(id);
		if (method != null) {
			return method;
		}
		return null;
	}

	/**
	 * 功能：默认
	 * */
	@ResponseBody
	@RequestMapping(value = "/defaults", method = RequestMethod.GET)
	public ResultMsg defaults(String id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.shoppingMethodService.editShoppingMethodDefaults(id)) {
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
		if (this.shoppingMethodService.deleteShoppingMethod(id)) {
			result.setResultMessage("success");
		}
		return result;
	}

}
