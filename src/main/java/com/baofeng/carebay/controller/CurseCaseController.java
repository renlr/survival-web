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

import com.baofeng.carebay.entity.CurseCase;
import com.baofeng.carebay.service.ICurseCaseService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;
import com.baofeng.utils.SearchFilter;
import com.baofeng.utils.SearchRule;

/**
 * 功能：护理套餐
 * */

@Controller
@RequestMapping("curse")
public class CurseCaseController {

	@Autowired
	private ICurseCaseService curseCaseService;

	public ICurseCaseService getCurseCaseService() {
		return curseCaseService;
	}

	public void setCurseCaseService(ICurseCaseService curseCaseService) {
		this.curseCaseService = curseCaseService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(String gid, String pid) {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/curseCase");
		mav.addObject("pid", pid);
		mav.addObject("gid", gid);
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
				search.setData(user.getChamber());
				search.setField("operator.chamber");
				search.setOp("eq");
				List<SearchRule> rules = new ArrayList<SearchRule>();
				rules.add(search);
				filter.setRules(rules);
			}
			pages = this.curseCaseService.readAllPages(rows, page, gid, filter);
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
			pages = this.curseCaseService.readPagesSkip(rows, page, filter);
		}
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(String gid, String pid, CurseCase curse, @RequestParam MultipartFile[] images, HttpServletRequest request) throws Exception {
		Operator operator = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/curseCase");
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
					curse.setImageSha1(sha1);
					curse.setImage(fileName);
				}
			}
		}
		curse.setOperator(operator);
		if (this.curseCaseService.addCurseCase(curse, gid)) {
			mav.addObject("gid", gid);
			mav.addObject("pid", pid);
			mav.addObject("result", "success");
		}
		return mav;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public CurseCase edit(String id) {
		CurseCase curse = this.curseCaseService.readCurseCase(id);
		if (curse != null) {
			return curse;
		}
		return null;
	}

	/**
	 * 功能：上下线
	 * */
	@ResponseBody
	@RequestMapping(value = "/online", method = RequestMethod.GET)
	public ResultMsg online(String id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.curseCaseService.onLineCurseCase(id)) {
			result.setResultStatus(200);
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
		if (this.curseCaseService.delCurseCase(id)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：添加业务详细内容
	 * */
	@RequestMapping(value = "/addCurseDetails", method = RequestMethod.GET)
	public ModelAndView addMusic(String id) {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/curseCaseDetails");
		CurseCase curse = this.curseCaseService.readCurseCase(id);
		if (curse != null) {
			mav.addObject("curse", curse);
		}
		return mav;
	}
}
