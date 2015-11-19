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

import com.baofeng.carebay.entity.ProductsCategory;
import com.baofeng.carebay.service.IProductsCategoryService;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;
import com.baofeng.utils.SearchFilter;
import com.baofeng.utils.SearchRule;

@Controller
@RequestMapping("productsCategory")
public class ProductsCategoryController {

	@Autowired
	private IProductsCategoryService productsCategoryService;

	public IProductsCategoryService getProductsCategoryService() {
		return productsCategoryService;
	}

	public void setProductsCategoryService(IProductsCategoryService productsCategoryService) {
		this.productsCategoryService = productsCategoryService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/productsCategory");
		return mav;
	}

	/**
	 * 功能：验证
	 * */
	@ResponseBody
	@RequestMapping(value = "/vaildation", method = RequestMethod.GET)
	public ResultMsg vaildation(String name) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.productsCategoryService.readProductsCategoryByName(name)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
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
			pages = this.productsCategoryService.readAllPages(rows, page, filter);
		}
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(String id, String name, String navigtor, HttpServletRequest request) throws Exception {
		Operator operator = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/productsCategory");
		if (this.productsCategoryService.addProductsCategory(id, name, navigtor, operator)) {
			mav.addObject("result", "success");
		}
		return mav;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ProductsCategory edit(String id) {
		ProductsCategory category = this.productsCategoryService.readProductsCategory(id);
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
		if (this.productsCategoryService.delProductsCategory(id)) {
			result.setResultMessage("success");
		}
		return result;
	}
}
