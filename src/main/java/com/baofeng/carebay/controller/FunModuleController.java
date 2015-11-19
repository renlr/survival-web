package com.baofeng.carebay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.carebay.entity.FunModule;
import com.baofeng.carebay.service.IFunModuleService;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;

@Controller
@RequestMapping("fmod")
public class FunModuleController {

	@Autowired
	private IFunModuleService funModuleService;

	public IFunModuleService getFunModuleService() {
		return funModuleService;
	}

	public void setFunModuleService(IFunModuleService funModuleService) {
		this.funModuleService = funModuleService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/funModule");
		return mav;
	}

	@RequestMapping(value = "/showTuiJian", method = RequestMethod.GET)
	public ModelAndView showTuiJian() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/funModuleTuiJian");
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows) {
		PageResult pages = this.funModuleService.readAllPages(rows, page, null);
		return pages;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/readPagesViews", method = RequestMethod.POST)
	public PageResult readPagesViews(int page, int rows) {
		PageResult pages = this.funModuleService.readPagesViews(rows, page);
		return pages;
	}

	/**
	 * 功能：验证是否以添加
	 * */
	@ResponseBody
	@RequestMapping(value = "/vildation", method = RequestMethod.GET)
	public ResultMsg vildation(String name) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		FunModule module = this.funModuleService.readFunModulebyName(name);
		if (module != null) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：验证是否已添加首页
	 * */
	@ResponseBody
	@RequestMapping(value = "/vildationViews", method = RequestMethod.GET)
	public ResultMsg vildationViews(String id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		FunModule module = this.funModuleService.readFunModulebyId(id);
		if (module != null) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：添加修改
	 * */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResultMsg save(FunModule idx) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.funModuleService.addFunModule(idx)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/saveIndexs", method = RequestMethod.POST)
	public ModelAndView saveIndexs(String id1, String indexs1) throws Exception {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/funModuleTuiJian");
		if (this.funModuleService.editIndexs(id1, indexs1)) {
			mav.addObject("result", "success");
		}
		return mav;
	}

	/**
	 * 功能：添加首页导航模块
	 * */
	@ResponseBody
	@RequestMapping(value = "/saveViews", method = RequestMethod.POST)
	public ResultMsg saveViews(String module, String indexs) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.funModuleService.addFunModuleViews(module, indexs)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public FunModule edit(String id) {
		FunModule fidx = this.funModuleService.readFunModule(id);
		if (fidx != null) {
			return fidx;
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
		if (this.funModuleService.deleteFunModule(id)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：删除
	 * */
	@ResponseBody
	@RequestMapping(value = "/deleteViews", method = RequestMethod.GET)
	public ResultMsg deleteViews(String id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.funModuleService.deleteFunModuleViews(id)) {
			result.setResultMessage("success");
		}
		return result;
	}
}
