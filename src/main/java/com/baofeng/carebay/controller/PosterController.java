package com.baofeng.carebay.controller;

import java.io.File;

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

import com.baofeng.carebay.entity.Poster;
import com.baofeng.carebay.entity.WeekService;
import com.baofeng.carebay.service.IPosterService;
import com.baofeng.carebay.service.IWeekServiceService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;

@Controller
@RequestMapping("poster")
public class PosterController {

	@Autowired
	private IPosterService posterService;

	@Autowired
	private IWeekServiceService weekServiceService;

	public IPosterService getPosterService() {
		return posterService;
	}

	public void setPosterService(IPosterService posterService) {
		this.posterService = posterService;
	}

	public IWeekServiceService getWeekServiceService() {
		return weekServiceService;
	}

	public void setWeekServiceService(IWeekServiceService weekServiceService) {
		this.weekServiceService = weekServiceService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/poster");
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows, String gid) {
		PageResult pages = this.posterService.readAllPages(rows, page, gid);
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(String id, String type, String indexs, String ductCat, String products, String funModule, String activity, String gid,
			@RequestParam MultipartFile[] images) throws Exception {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/poster");
		Poster post = new Poster();
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
					post.setImage(fileName);
					post.setImageSha1(sha1);
				}
			}
		}
		post.setId(id);
		post.setType(Integer.valueOf(type));
		post.setIndexs(Integer.valueOf(indexs));
		if (this.posterService.addPoster(post, type, ductCat, products, funModule, activity, gid)) {
			WeekService week = this.weekServiceService.readWeekService(gid);
			mav.addObject("week", week);
		}
		return mav;
	}

	/**
	 * 功能：编辑
	 * */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public Poster edit(String id) {
		Poster post = this.posterService.readPoster(id);
		if (post != null) {
			return post;
		}
		return null;
	}

	/**
	 * 功能：编辑显示顺序
	 * */
	@RequestMapping(value = "/saveIndexs", method = RequestMethod.POST)
	public ModelAndView saveIndexs(String id1, String indexs1, String gid1) {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/poster");
		if (this.posterService.editIndexs(id1, indexs1)) {
			WeekService week = this.weekServiceService.readWeekService(gid1);
			mav.addObject("week", week);
			mav.addObject("result", "success");
		}
		return mav;
	}

	/**
	 * 功能：上下线
	 * */
	@ResponseBody
	@RequestMapping(value = "/online", method = RequestMethod.GET)
	public ResultMsg online(String id) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.posterService.onLinePoster(id)) {
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
		if (this.posterService.delPoster(id)) {
			result.setResultMessage("success");
		}
		return result;
	}
}
