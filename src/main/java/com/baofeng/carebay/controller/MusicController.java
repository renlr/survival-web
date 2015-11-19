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

import com.baofeng.carebay.entity.Music;
import com.baofeng.carebay.entity.WeekService;
import com.baofeng.carebay.service.IMusicService;
import com.baofeng.carebay.service.IWeekServiceService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.ResultMsg;

@Controller
@RequestMapping("/music")
public class MusicController extends BaseController {

	@Autowired
	private IMusicService musicService;

	@Autowired
	private IWeekServiceService weekServiceService;

	public IMusicService getMusicService() {
		return musicService;
	}

	public void setMusicService(IMusicService musicService) {
		this.musicService = musicService;
	}

	public IWeekServiceService getWeekServiceService() {
		return weekServiceService;
	}

	public void setWeekServiceService(IWeekServiceService weekServiceService) {
		this.weekServiceService = weekServiceService;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/music");
		return mav;
	}

	/**
	 * 功能：分页数据
	 * */
	@ResponseBody
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/readPages", method = RequestMethod.POST)
	public PageResult readPages(int page, int rows, String gid) {
		PageResult pages = this.musicService.readAllPages(rows, page, gid);
		return pages;
	}

	/**
	 * 功能：添加修改
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@RequestParam MultipartFile[] images, @RequestParam MultipartFile[] musics, String describes, String gid, String $name) throws Exception {
		ModelAndView mav = new ModelAndView(com.baofeng.utils.Constants.COREWEB_BUILDITEMS + "/music");
		Music music = new Music();
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
		if (musics != null && musics.length > 0) {
			for (MultipartFile $file : musics) {
				if (!$file.isEmpty()) {
					String name = $file.getOriginalFilename();
					String ext = name.substring(name.lastIndexOf("."), name.length());
					String sha1 = DigestUtils.shaHex($file.getInputStream());
					String fileName = sha1 + ext;
					String path = Constants.DEFAULT_CAREBAYMUSIC + File.separator + Constants.sha1ToPath(sha1);
					Constants.mkdirs(path);
					FileUtils.copyInputStreamToFile($file.getInputStream(), new File(path, fileName));
					if ($name == null || $name.trim().length() > 0)
						music.setName(name.substring(0, name.lastIndexOf(".")));
					music.setFileSha1(sha1);
					music.setFile(fileName);
				}
			}
		}
		if ($name != null && $name.trim().length() > 0)
			music.setName($name);
		music.setDescribes(describes);
		this.musicService.addMusic(music, gid);
		WeekService week = this.weekServiceService.readWeekService(gid);
		if (week != null) {
			mav.addObject("week", week);
		}
		return mav;
	}

	/**
	 * 功能：修改图片
	 * */
	@ResponseBody
	@RequestMapping(value = "/upLoadImages", method = RequestMethod.POST)
	public ResultMsg upLoadImages(String id2, @RequestParam MultipartFile[] images2, String gid) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		Music music = new Music();
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
		if (this.musicService.addMusic(music, gid)) {
			result.setResultMessage("success");
		}
		return result;
	}

	/**
	 * 功能：按字段修改
	 * */
	@ResponseBody
	@RequestMapping(value = "/saveEditFields", method = RequestMethod.POST)
	public ResultMsg saveEditField(String id, String field, String value) throws Exception {
		ResultMsg result = new ResultMsg();
		result.setResultMessage("errors");
		if (this.musicService.editFiles(id, field, value)) {
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
		if (this.musicService.deleteMusic(id)) {
			result.setResultMessage("success");
		}
		return result;
	}
}
