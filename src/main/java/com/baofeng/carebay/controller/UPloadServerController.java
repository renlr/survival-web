package com.baofeng.carebay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.carebay.entity.UPloadServer;
import com.baofeng.carebay.service.IUPloadServerService;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;

@Controller
@RequestMapping("upserver")
public class UPloadServerController {

	@Autowired
	private IUPloadServerService uploadServerService;

	public IUPloadServerService getUploadServerService() {
		return uploadServerService;
	}

	public void setUploadServerService(IUPloadServerService uploadServerService) {
		this.uploadServerService = uploadServerService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/upserver");
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows) {
		PageResult pages = this.uploadServerService.readAllPages(rows, page, null);
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(UPloadServer upserver) throws Exception {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/upserver");
		if (this.uploadServerService.addUPloadServer(upserver)) {
			mav.addObject("result", "success");
		}
		return mav;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public UPloadServer edit(String id) {
		UPloadServer upserver = this.uploadServerService.editUPloadServer(id);
		if (upserver != null) {
			return upserver;
		}
		return null;
	}

	/**
	 * 功能：删除
	 * */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(String id) {
		if (this.uploadServerService.delUPloadServer(id)) {
			return "success";
		}
		return "error";
	}

}
