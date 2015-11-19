package com.baofeng.carebay.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.baofeng.carebay.entity.CustomService;
import com.baofeng.carebay.service.ICustomService;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;
import com.baofeng.utils.SearchFilter;
import com.baofeng.utils.SearchRule;

@Controller
@RequestMapping("cs")
public class CustomServiceController {

	@Autowired
	private ICustomService customService;

	public ICustomService getCustomService() {
		return customService;
	}

	public void setCustomService(ICustomService customService) {
		this.customService = customService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(String gid) {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/customService");
		mav.addObject("gid", gid);
		return mav;
	}

	@RequestMapping(value = "/goback", method = RequestMethod.GET)
	public ModelAndView goback(String id) {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/curseToS");
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows, String gid, HttpServletRequest request) {
		PageResult pages = null;
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null) {
			SearchFilter filter = new SearchFilter();
			if (user.getChamber() != null && user.getChamber().trim().length() > 0) {
				SearchRule search = new SearchRule();
				search.setData(user.getId());
				search.setField("operator.id");
				search.setOp("eq");
				List<SearchRule> rules = new ArrayList<SearchRule>();
				rules.add(search);
				filter.setRules(rules);
			}
			pages = this.customService.readAllPages(rows, page, gid, filter);
		}
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(String id, String gid, String name, String indexs, @RequestParam MultipartFile[] images, HttpServletRequest request) throws Exception {
		Operator operator = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/customService");
		CustomService custom = new CustomService();
		if (images != null && images.length > 0) {
			for (MultipartFile $file : images) {
				if (!$file.isEmpty()) {
					String $name = $file.getOriginalFilename();
					String ext = $name.substring($name.lastIndexOf("."), $name.length());
					String sha1 = DigestUtils.shaHex($file.getInputStream());
					String fileName = sha1 + ext;
					String path = Constants.DEFAULT_UPLOADIMAGEPATH + File.separator + Constants.sha1ToPath(sha1);
					Constants.mkdirs(path);
					FileUtils.copyInputStreamToFile($file.getInputStream(), new File(path, fileName));
					custom.setImage(fileName);
					custom.setImageSha1(sha1);
				}
			}
		}
		custom.setId(id);
		custom.setName(name);
		custom.setOperator(operator);
		custom.setIndexs(Integer.valueOf(indexs));
		if (this.customService.addCustomService(custom, gid)) {
			mav.addObject("gid", gid);
			mav.addObject("result", "success");
		}
		return mav;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public CustomService read(String id) {
		CustomService custom = this.customService.readCustomService(id);
		if (custom != null) {
			return custom;
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
		if (this.customService.deleteCustomService(id)) {
			msg.setResultStatus(200);
			msg.setResultMessage("success");
		}
		return msg;
	}

	/**
	 * 功能：上下线
	 * */
	@ResponseBody
	@RequestMapping(value = "/online", method = RequestMethod.GET)
	public ResultMsg online(String id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.customService.onLineCustomService(id)) {
			result.setResultStatus(200);
			result.setResultMessage("success");
		}
		return result;
	}

}
