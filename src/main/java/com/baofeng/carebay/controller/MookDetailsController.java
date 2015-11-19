package com.baofeng.carebay.controller;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.carebay.entity.Mook;
import com.baofeng.carebay.entity.MookDetails;
import com.baofeng.carebay.service.IMookDetailsService;
import com.baofeng.carebay.service.IMookService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;

@Controller
@RequestMapping("mookDetails")
public class MookDetailsController {

	@Autowired
	private IMookService mookService;
	@Autowired
	private IMookDetailsService mookDetailsService;

	public IMookService getMookService() {
		return mookService;
	}

	public void setMookService(IMookService mookService) {
		this.mookService = mookService;
	}

	public IMookDetailsService getMookDetailsService() {
		return mookDetailsService;
	}

	public void setMookDetailsService(IMookDetailsService mookDetailsService) {
		this.mookDetailsService = mookDetailsService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/mookDetails");
		return mav;
	}

	@RequestMapping(value = "/editContent", method = RequestMethod.GET)
	public ModelAndView editContent(String id, String gid) {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/mookContent");
		MookDetails details = this.mookDetailsService.readMookDetails(id);
		Mook mook = this.mookService.readMook(gid);
		details.setContent("");
		mav.addObject("mook", mook);
		mav.addObject("details", details);
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows, String gid) {
		PageResult pages = this.mookDetailsService.readAllPages(rows, page, gid);
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResultMsg save(String id, String name, String gid) throws Exception {
		ResultMsg result = new ResultMsg();
		MookDetails details = new MookDetails();
		details.setId(id);
		details.setName(Integer.valueOf(name));
		if (this.mookDetailsService.addMookDetails(details, gid)) {
			result.setResultMessage("success");
		}
		return result;
	}
	
	/**
	 * 功能：编辑内容
	 * */
	@ResponseBody
	@RequestMapping(value = "/saveContent", method = RequestMethod.POST)
	public ResultMsg saveContent(String id, String gid, String content) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		StringBuffer $temp = new StringBuffer();
		$temp.append(Constants.HTMLCENTERCODE);
		MookDetails details = new MookDetails();
		if(content != null && content.trim().length() > 0){
			File file = new File(Constants.DEFAULT_BUILDHTMLPATH + File.separator + id + ".html");
			$temp.append(content);
			FileUtils.writeStringToFile(file, $temp.toString(), "utf-8");
			details.setContent(id + ".html");
		}
		if(this.mookDetailsService.addMookDetailsContent(id,gid,content)){
			result.setResultMessage("success");
		}
		return result;
	}
	
	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public MookDetails edit(String id) {
		MookDetails details = this.mookDetailsService.readMookDetails(id);
		if (details != null) {
			return details;
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
		if (this.mookDetailsService.deleteMookDetails(id)) {
			msg.setResultMessage("success");
		}
		return msg;
	}

}
