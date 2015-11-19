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

import com.baofeng.carebay.entity.Mook;
import com.baofeng.carebay.service.IMookService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;
import com.baofeng.utils.SearchFilter;
import com.baofeng.utils.SearchRule;

@Controller
@RequestMapping("mook")
public class MookController {

	@Autowired
	private IMookService mookService;

	public IMookService getMookService() {
		return mookService;
	}

	public void setMookService(IMookService mookService) {
		this.mookService = mookService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/mook");
		return mav;
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
			pages = this.mookService.readAllPages(rows, page, filter);
		}
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(String id, String content, String vip, String dateTime, String indexs, String type, String products, String custom, String services, String mealId,
			String activity, @RequestParam MultipartFile[] images, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/mook");
		Operator user = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (user != null) {
			Mook mook = new Mook();
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
						mook.setImage(fileName);
						mook.setImageSha1(sha1);
					}
				}
			}
			mook.setId(id);
			mook.setName(content);
			mook.setType(type);
			if ("1".equals(type)) {
				mook.setParams(products);
			} else if ("2".equals(type)) {
				mook.setParams(custom);
			} else if ("3".equals(type)) {
				mook.setParams(services);
			} else if ("4".equals(type)) {
				mook.setParams(mealId);
			} else if ("5".equals(type)) {
				mook.setParams(activity);
			}
			if (indexs != null && indexs.trim().length() > 0)
				mook.setIndexs(Integer.valueOf(indexs));
			mook.setOperator(user);
			if (this.mookService.addMook(mook)) {
				mav.addObject("result", "success");
			}
		}
		return mav;
	}

	/**
	 * 功能：添加期刊内容
	 * */
	@RequestMapping(value = "/addMookDetails", method = RequestMethod.GET)
	public ModelAndView addMookDetails(String id) {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/mookDetails");
		Mook journal = this.mookService.readMook(id);
		if (journal != null) {
			mav.addObject("mook", journal);
		}
		return mav;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public Mook edit(String id) {
		Mook mook = this.mookService.readMook(id);
		if (mook != null) {
			return mook;
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
		if (this.mookService.onLineBabyJournal(id)) {
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
		ResultMsg msg = new ResultMsg();
		msg.setResultMessage("errors");
		if (this.mookService.deleteBabyJournal(id)) {
			msg.setResultMessage("success");
		}
		return msg;
	}

	/**
	 * 功能：修改显示顺序
	 * */
	@RequestMapping(value = "/saveIndexs", method = RequestMethod.POST)
	public ModelAndView saveIndexs(String id1, String indexs1) throws Exception {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/mook");
		if (this.mookService.updateMook(id1, indexs1)) {
			mav.addObject("result", "success");
		}
		return mav;
	}
}
