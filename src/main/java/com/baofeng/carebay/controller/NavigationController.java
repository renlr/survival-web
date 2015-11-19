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

import com.baofeng.carebay.entity.Navigation;
import com.baofeng.carebay.service.INavigationService;
import com.baofeng.commons.entity.Operator;
import com.baofeng.utils.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;
import com.baofeng.utils.SearchFilter;
import com.baofeng.utils.SearchRule;

/**
 * 功能：菜单导航
 * */
@Controller
@RequestMapping("naviga")
public class NavigationController {

	@Autowired
	private INavigationService navigationService;

	public INavigationService getNavigationService() {
		return navigationService;
	}

	public void setNavigationService(INavigationService navigationService) {
		this.navigationService = navigationService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(String gid) {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/navigation");
		if (gid != null) {
			mav.addObject("gid", gid);
		}
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
			pages = this.navigationService.readAllPages(rows, page, gid, filter);
		}
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(String id, String gid, String name, String navId, String nkey, String indexs, @RequestParam MultipartFile[] images, HttpServletRequest request)
			throws Exception {
		ModelAndView mav = new ModelAndView(Constants.COREWEB_BUILDITEMS + "/navigation");
		Navigation nav = new Navigation();
		Operator operator = (Operator) request.getSession().getAttribute(Constants.CURRENT_USER);
		if (operator != null) {
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
						nav.setImage(fileName);
						nav.setImageSha1(sha1);
					}
				}
			}
			nav.setId(id);
			nav.setName(name);
			nav.setNavId(navId);
			nav.setNkey(nkey);
			nav.setIndexs(Integer.valueOf(indexs));
			nav.setOperator(operator);
			if (this.navigationService.addNavigation(gid, nav)) {
				mav.addObject("gid", gid);
				mav.addObject("result", "success");
			}
		}
		return mav;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public Navigation read(String id) {
		Navigation nav = this.navigationService.readNavigation(id);
		if (nav != null) {
			return nav;
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
		if (this.navigationService.deleteNavigation(id)) {
			msg.setResultStatus(200);
			msg.setResultMessage("success");
		}
		return msg;
	}

	/**
	 * 功能：下拉选择
	 * */
	@ResponseBody
	@RequestMapping(value = "/readLabelList", method = RequestMethod.POST)
	public List<Navigation> readLabelList() {
		List<Navigation> navigtorList = this.navigationService.readNavigtorList("CAREBAY_SHOPPING");
		if (navigtorList != null && navigtorList.size() > 0) {
			return navigtorList;
		}
		return new ArrayList<Navigation>();
	}

}
