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

import com.baofeng.carebay.entity.MusicBg;
import com.baofeng.carebay.entity.WeekService;
import com.baofeng.carebay.service.IMusicBgService;
import com.baofeng.carebay.service.IWeekServiceService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;

@Controller
@RequestMapping("musicbg")
public class MusicBgController {

	@Autowired
	private IMusicBgService musicBgService;
	@Autowired
	private IWeekServiceService weekServiceService;

	public IMusicBgService getMusicBgService() {
		return musicBgService;
	}

	public void setMusicBgService(IMusicBgService musicBgService) {
		this.musicBgService = musicBgService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/musicbg");
		return mav;
	}

	public IWeekServiceService getWeekServiceService() {
		return weekServiceService;
	}

	public void setWeekServiceService(IWeekServiceService weekServiceService) {
		this.weekServiceService = weekServiceService;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows, String gid) {
		PageResult pages = this.musicBgService.readAllPages(rows, page, gid);
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@RequestParam MultipartFile[] images, String gid) throws Exception {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/musicbg");
		MusicBg music = new MusicBg();
		if (images != null && images.length > 0) {
			for (MultipartFile $file : images) {
				if (!$file.isEmpty()) {
					String name = $file.getOriginalFilename();
					String ext = name.substring(name.lastIndexOf("."), name.length());
					String sha1 = DigestUtils.shaHex($file.getInputStream());
					String fileName = sha1 + ext;
					String path = Constants.DEFAULT_UPLOADIMAGEPATH + File.separator + Constants.sha1ToPath(sha1);
					Constants.mkdirs(path);
					FileUtils.copyInputStreamToFile($file.getInputStream(), new File(path, fileName));
					music.setImageSha1(sha1);
					music.setImage(fileName);
				}
			}
		}
		this.musicBgService.addMusicBg(music, gid);
		WeekService week = this.weekServiceService.readWeekService(gid);
		if (week != null) {
			mav.addObject("week", week);
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
		if (this.musicBgService.onLineMusicBg(id)) {
			result.setResultStatus(200);
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：修改图片
	 * */
	@ResponseBody
	@RequestMapping(value = "/upLoadImages", method = RequestMethod.POST)
	public ResultMsg upLoadImages(String id2, @RequestParam MultipartFile[] images2, String gid) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		MusicBg music = new MusicBg();
		if (images2 != null && images2.length > 0) {
			for (MultipartFile $file : images2) {
				if (!$file.isEmpty()) {
					String name = $file.getOriginalFilename();
					String ext = name.substring(name.lastIndexOf("."), name.length());
					String sha1 = DigestUtils.shaHex($file.getInputStream());
					String fileName = sha1 + ext;
					String path = Constants.DEFAULT_UPLOADIMAGEPATH + File.separator + Constants.sha1ToPath(sha1);
					Constants.mkdirs(path);
					FileUtils.copyInputStreamToFile($file.getInputStream(), new File(path, fileName));
					music.setImageSha1(sha1);
					music.setImage(fileName);
				}
			}
		}
		music.setId(id2);
		if (this.musicBgService.addMusicBg(music, gid)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：删除
	 * */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ResultMsg delete(String id, String gid) {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.musicBgService.deleteMusicBg(id)) {
			result.setResultMessage("success");
		}
		return result;
	}

}
