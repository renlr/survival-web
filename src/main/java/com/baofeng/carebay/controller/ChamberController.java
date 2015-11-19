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

import com.baofeng.carebay.entity.Chamber;
import com.baofeng.carebay.entity.ChamberDetails;
import com.baofeng.carebay.service.IChamberService;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;
import com.baofeng.utils.SearchFilter;
import com.baofeng.utils.SearchRule;

@Controller
@RequestMapping("chamber")
public class ChamberController {

	@Autowired
	private IChamberService chamberService;

	public void setChamberService(IChamberService chamberService) {
		this.chamberService = chamberService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/chamber");
		return mav;
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(String inds, String gid) {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/chamberDetails");
		mav.addObject("gid", gid);
		mav.addObject("inds", inds);
		if ("1".equals(inds)) {
			mav.addObject("name", "会所介绍");
		} else if ("2".equals(inds)) {
			mav.addObject("name", "会员手册");
		}
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
				search.setField("id");
				search.setOp("eq");
				List<SearchRule> rules = new ArrayList<SearchRule>();
				rules.add(search);
				filter.setRules(rules);
			}
			pages = this.chamberService.readAllPages(rows, page, filter);
		}
		return pages;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@RequestMapping(value = "/readPagesDetails", method = RequestMethod.POST)
	public PageResult readPagesDetails(int page, int rows, String gid, String inds) {
		PageResult pages = this.chamberService.readAllPagesDetails(rows, page, gid, inds);
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(String id, String name, String address, String telPhone, String manager, String leavePWD, String resetPWD) throws Exception {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/chamber");
		if (this.chamberService.addChamber(id, name, address, telPhone, manager, leavePWD, resetPWD)) {
			mav.addObject("result", "success");
		}
		return mav;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/saveDetails", method = RequestMethod.POST)
	public ModelAndView saveDetails(String id, String gid, String inds, String indexs, String indexTerms, @RequestParam MultipartFile[] images) throws Exception {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/chamberDetails");
		ChamberDetails details = new ChamberDetails();
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
		details.setIndexs(Integer.valueOf(indexs));
		if (this.chamberService.addChamberDetails(details, gid, inds, indexTerms)) {
			mav.addObject("gid", gid);
			mav.addObject("inds", inds);
			if ("1".equals(inds)) {
				mav.addObject("name", "会所介绍");
			} else if ("2".equals(inds)) {
				mav.addObject("name", "会员手册");
			}
			mav.addObject("result", "success");
		}
		return mav;
	}

	/**
	 * 功能：会所列表
	 * */
	@ResponseBody
	@RequestMapping(value = "/readList", method = RequestMethod.POST)
	public List<Chamber> readList(HttpServletRequest request) {
		List<Chamber> berList = null;
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null) {
			SearchFilter filter = new SearchFilter();
			if (user.getChamber() != null && user.getChamber().trim().length() > 0) {
				SearchRule search = new SearchRule();
				search.setData(user.getChamber());
				search.setField("id");
				search.setOp("eq");
				List<SearchRule> rules = new ArrayList<SearchRule>();
				rules.add(search);
				filter.setRules(rules);
			}
			berList = this.chamberService.readChamberList(filter);
		}
		if (berList != null) {
			return berList;
		}
		return null;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public Chamber read(String id) {
		Chamber chamber = this.chamberService.readChamber(id);
		if (chamber != null) {
			return chamber;
		}
		return null;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/editDetails", method = RequestMethod.GET)
	public ChamberDetails editDetails(String id) {
		ChamberDetails details = this.chamberService.readChamberDetails(id);
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
		if (this.chamberService.deleteChamber(id)) {
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
		if (this.chamberService.onLineChamberDetails(id)) {
			result.setResultStatus(200);
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：删除
	 * */
	@ResponseBody
	@RequestMapping(value = "/deleteDetails", method = RequestMethod.GET)
	public ResultMsg deleteDetails(String id) {
		ResultMsg msg = new ResultMsg();
		msg.setResultMessage("errors");
		if (this.chamberService.deleteChamberDetails(id)) {
			msg.setResultMessage("success");
		}
		return msg;
	}

}
