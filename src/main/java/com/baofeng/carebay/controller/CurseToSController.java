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

import com.baofeng.carebay.entity.CurseToS;
import com.baofeng.carebay.service.ICurseToSService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;
import com.baofeng.utils.SearchFilter;
import com.baofeng.utils.SearchRule;

/**
 * 功能：服务类型
 * */

@Controller
@RequestMapping("tos")
public class CurseToSController {

	@Autowired
	private ICurseToSService curseToSService;

	public ICurseToSService getCurseToSService() {
		return curseToSService;
	}

	public void setCurseToSService(ICurseToSService curseToSService) {
		this.curseToSService = curseToSService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/curseToS");
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
			pages = this.curseToSService.readAllPages(rows, page, filter);
		}
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(String id, String name, String indexs, @RequestParam MultipartFile[] images, HttpServletRequest request) throws Exception {
		Operator operator = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/curseToS");
		CurseToS curseTos = new CurseToS();
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
					curseTos.setImageSha1(sha1);
					curseTos.setImage(fileName);
				}
			}
		}
		curseTos.setId(id);
		curseTos.setName(name);
		curseTos.setOperator(operator);
		curseTos.setIndexs(Integer.valueOf(indexs));
		if (this.curseToSService.addCurseTos(curseTos)) {
			mav.addObject("result", "success");
		}
		return mav;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public CurseToS edit(String id) {
		CurseToS curse = this.curseToSService.readCurseToS(id);
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
		if (this.curseToSService.onLineCurseToS(id)) {
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
		if (this.curseToSService.delCurseToS(id)) {
			result.setResultMessage("success");
		}
		return result;
	}

}
