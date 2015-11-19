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

import com.baofeng.carebay.entity.CustomMade;
import com.baofeng.carebay.entity.CustomMadeDetails;
import com.baofeng.carebay.service.ICustomMadeService;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;
import com.baofeng.utils.SearchFilter;
import com.baofeng.utils.SearchRule;

@Controller
@RequestMapping("custom")
public class CustomMadeController {

	@Autowired
	private ICustomMadeService customMadeService;

	public ICustomMadeService getCustomMadeService() {
		return customMadeService;
	}

	public void setCustomMadeService(ICustomMadeService customMadeService) {
		this.customMadeService = customMadeService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/customMade");
		return mav;
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(String gid) {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/customMadeDetails");
		mav.addObject("gid", gid);
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
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
			pages = this.customMadeService.readAllPages(rows, page, filter);
		}
		return pages;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/readPagesSkip", method = RequestMethod.POST)
	public PageResult readPagesSkip(int page, int rows, HttpServletRequest request) {
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
			pages = this.customMadeService.readPagesSkip(rows, page, filter);
		}
		return pages;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/readPagesDetails", method = RequestMethod.POST)
	public PageResult readPagesDetails(int page, int rows, String gid, HttpServletRequest request) {
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
			pages = this.customMadeService.readAllPagesDetails(rows, page, gid, filter);
		}
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(String id, String name, String indexs, @RequestParam MultipartFile[] images, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/customMade");
		Operator operator = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		CustomMade custom = new CustomMade();
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
		if (indexs != null && indexs.trim().length() > 0)
			custom.setIndexs(Integer.valueOf(indexs));
		if (this.customMadeService.addCustomMade(custom)) {
			mav.addObject("result", "success");
		}
		return mav;
	}

	/**
	 * 功能：添加定制内容
	 * */
	@RequestMapping(value = "/saveDetails", method = RequestMethod.POST)
	public ModelAndView saveDetails(String id, String gid, String name, String content, String indexs, @RequestParam MultipartFile[] images, HttpServletRequest request)
			throws Exception {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/customMadeDetails");
		Operator operator = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		CustomMadeDetails details = new CustomMadeDetails();
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
					details.setImage(fileName);
					details.setImageSha1(sha1);
				}
			}
		}
		details.setId(id);
		details.setName(name);
		details.setContent(content);
		details.setOperator(operator);
		details.setIndexs(Integer.valueOf(indexs));
		if (this.customMadeService.addCustomMadeDetails(details, gid)) {
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
	public CustomMade read(String id) {
		CustomMade custom = this.customMadeService.readCustomMade(id);
		if (custom != null) {
			return custom;
		}
		return null;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/editDetails", method = RequestMethod.GET)
	public CustomMadeDetails readDetails(String id) {
		CustomMadeDetails custom = this.customMadeService.readCustomMadeDetails(id);
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
		if (this.customMadeService.deleteCustomMade(id)) {
			msg.setResultStatus(200);
			msg.setResultMessage("success");
		}
		return msg;
	}

	/**
	 * 功能：删除
	 * */
	@ResponseBody
	@RequestMapping(value = "/deleteDetails", method = RequestMethod.GET)
	public ResultMsg deleteDetails(String id) {
		ResultMsg msg = new ResultMsg();
		msg.setResultMessage("errors");
		if (this.customMadeService.deleteCustomMadeDetails(id)) {
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
		if (this.customMadeService.onLineCustomMade(id)) {
			result.setResultStatus(200);
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：上下线
	 * */
	@ResponseBody
	@RequestMapping(value = "/onlineDetails", method = RequestMethod.GET)
	public ResultMsg onlineDetails(String id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.customMadeService.onLineCustomMadeDetails(id)) {
			result.setResultStatus(200);
			result.setResultMessage("success");
		}
		return result;
	}

}
